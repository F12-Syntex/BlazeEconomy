package com.devnecs.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.devnecs.economy.Account;
import com.devnecs.economy.Transaction;
import com.devnecs.economy.TransactionType;
import com.devnecs.main.Blaze;

public class Sqlite {

	private File database;
	
	private Connection connection;
	
	private String AccountsTable = "users";
	private String TransactionTable = "transactions";
	
	public Sqlite() {
		this.setDatabase(Blaze.getInstance().configManager.sqlite_storage);
	}
	
	public boolean isConnected() {
		return connection != null;
	}
	
	public void connect() throws SQLException {
        Blaze.getInstance().getLogger().info("Connecting.");
	        if (!database.exists()){
	            try {
	            	database.createNewFile();
	            } catch (IOException e) {
	            	Blaze.getInstance().getLogger().info("File write error: "+ database.getName() +".db");
	            }
	        }
	        try {
	            if(connection!=null&&!connection.isClosed()){
		           Blaze.getInstance().getLogger().info("Database is already connected");
	               return; 
	            }
	            Class.forName("org.sqlite.JDBC");
	            connection = DriverManager.getConnection("jdbc:sqlite:" + database);
	            Blaze.getInstance().getLogger().info("Database connected");
	        } catch (SQLException ex) {
	        	Blaze.getInstance().getLogger().info("SQLite exception on initialize: " + ex.getLocalizedMessage());
	        } catch (ClassNotFoundException ex) {
	        	Blaze.getInstance().getLogger().info("You need the SQLite JBDC library. Google it. Put it in /lib folder.");
	        }
			
	}
	
	public List<Account> getAccounts() {
		
		List<Account> accounts = new ArrayList<Account>();
		
		try {

			this.createDatabase();
			
			PreparedStatement keys = this.getConnection().prepareStatement("SELECT UUID FROM " + AccountsTable);
			ResultSet results = keys.executeQuery();
			
			while(results.next()) {
				
				UUID result = UUID.fromString(results.getString(results.getRow()));
				
				PreparedStatement balanceQuery = this.getConnection().prepareStatement("SELECT BALANCE FROM " + AccountsTable + " WHERE UUID=?");
				balanceQuery.setString(1, result.toString());
				
				ResultSet balanceResult = balanceQuery.executeQuery();
				
				if(!balanceResult.next()) {
					Bukkit.getServer().shutdown();
					return accounts;
				}
				
				double balance = balanceResult.getDouble(balanceResult.getRow());
				
				PreparedStatement transactionQuery = this.getConnection().prepareStatement("SELECT * FROM " + TransactionTable + " WHERE UUID=?");
				transactionQuery.setString(1, result.toString());
				ResultSet transactions = transactionQuery.executeQuery();
				
				List<Transaction> instances =  new ArrayList<Transaction>();
				
				while(transactions.next()) {
					
					//String TransactionID = transactions.getString(transactions.getRow());
					
					Transaction data = new Transaction(transactions.getLong("DATE"),
														transactions.getDouble("AMOUNT"),
														UUID.fromString(transactions.getString("SENDER")),
														TransactionType.valueOf(transactions.getString("LOGS")));
					
					instances.add(data);
					
				}
				
				
				Account account = new Account(result, balance, instances);
				accounts.add(account);
				
				
				
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return accounts;
	}
	
	public void createDatabase() {
		PreparedStatement preparedStatement;
		
		try {
		
			preparedStatement = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + AccountsTable + " (UUID VARCHAR(37),BALANCE DOUBLE(50, 10),PRIMARY KEY(UUID))");
			preparedStatement.executeUpdate();
			
			preparedStatement = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + TransactionTable 
			+ "(ID VARCHAR(37), UUID VARCHAR(37), DATE BIGINT(15), AMOUNT DOUBLE(50, 10), SENDER VARCHAR(37), LOGS VARCHAR(10), PRIMARY KEY(ID))");
			preparedStatement.executeUpdate();
	
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		new Thread(() -> {
			
			PreparedStatement preparedStatement;
			
			try {
				
				this.createDatabase();
				
				for(Account account : Blaze.getInstance().economyHandler.getBalance()) {
					preparedStatement = this.getConnection().prepareStatement("SELECT * FROM " + AccountsTable + " WHERE UUID=?");
					preparedStatement.setString(1, account.getKey().toString());
					ResultSet results = preparedStatement.executeQuery();
					if(!results.next()) {
						PreparedStatement insert = this.getConnection().prepareStatement("INSERT INTO " + AccountsTable + " (UUID,BALANCE) VALUES (?,?)");
						
						insert.setString(1, account.getKey().toString());
						insert.setDouble(2, account.getBalance());
						insert.executeUpdate();
					}else {
						PreparedStatement insert = this.getConnection().prepareStatement("UPDATE " + AccountsTable + " SET BALANCE=? WHERE UUID=?");
						
						insert.setDouble(1, account.getBalance());
						insert.setString(2, account.getKey().toString());
						
						insert.executeUpdate();
					}

					PreparedStatement reset = this.getConnection().prepareStatement("DELETE FROM " + TransactionTable + " WHERE UUID=?");
					reset.setString(1, account.getKey().toString());
					reset.executeUpdate();
						
					for(Transaction transaction : account.getTransations()) {
						PreparedStatement insert = this.getConnection().prepareStatement("INSERT INTO " + TransactionTable + " (ID,UUID,DATE,AMOUNT,SENDER,LOGS) VALUES (?,?,?,?,?,?)");
						
						insert.setString(1, UUID.randomUUID().toString()+"");
						insert.setString(2, account.getKey().toString());
						insert.setLong(3, transaction.getDate());
						insert.setDouble(4, transaction.getAmount());
						insert.setString(5, transaction.getSender().toString());
						insert.setString(6, transaction.getTransactionType().name());
						
						insert.executeUpdate();	
					}

					
				}
				
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}).run();
	}
	
	public boolean AccountsUuidExists(UUID uuid) {
		try {
			
			PreparedStatement preparedStatement = this.getConnection().prepareStatement("SELECT FROM " + AccountsTable + " WHERE UUID=?");
			preparedStatement.setString(1, uuid.toString());
			ResultSet results = preparedStatement.executeQuery();
			return results.next();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void disconnect() {
		if(this.isConnected()) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setup() {
		
	}
	
	public Connection getConnection() {
		return this.connection;
	}

	public File getDatabase() {
		return database;
	}

	public void setDatabase(File database) {
		this.database = database;
	}
	
}
