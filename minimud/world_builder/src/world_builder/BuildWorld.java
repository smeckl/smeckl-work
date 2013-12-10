package world_builder;


import minimud_shared.RegularExpressions;

public class BuildWorld 
{
    
    enum ImportType
    {
        Invalid,
        Update,
        Reset
    };
    
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		System.out.println("Starting Mini MUD server.");

		int nPort = 0;
		String strUser = "";
		String strPassword = "";
		String strDataFile = "./world_data.xml";
		String strDBServer = "";
        
        ImportType impType = ImportType.Invalid;
        
		
		RegularExpressions regEx = new RegularExpressions();
		
		DatabaseConnector m_dbConn = null;
		
		while(true)
		{
			if(args.length < 4)
			{
				System.out.println("Invalid number of arguments.");
				break;
			}
			
			// Validate server format
			if(regEx.stringMatchesRegEx(args[0], RegularExpressions.RegExID.IP)
					|| regEx.stringMatchesRegEx(args[0], RegularExpressions.RegExID.DOMAIN))
			{
				strDBServer = args[0];
			}
			else
			{
				System.out.println("Invalid server name or IP address specified.");
				break;
			}
			
			// Validate port number format
			if(regEx.stringMatchesRegEx(args[1], RegularExpressions.RegExID.PORT))
			{
				nPort = Integer.parseInt(args[1]);
			}
			else
			{
				System.out.println("Invalid port number specified.");
				break;
			}
            
            if(0 == args[2].compareTo("reset"))
            {
                impType = ImportType.Reset;
            }
            else if(0 == args[2].compareTo("update"))
            {
                impType = ImportType.Update;
            }
            
			// Validate user name format
			if(regEx.stringMatchesRegEx(args[3], RegularExpressions.RegExID.USERNAME))
			{
				strUser = args[3];
			}
			else
			{
				System.out.println("Invalid user name.");
				break;
			}
			
			if(args.length == 6)
            {
                strPassword = args[4];
                
                strDataFile = args[5];
            }
            else
            {
                // Have the user enter the password
                System.out.print("Enter the password to the database: ");

                char szPwd[] = System.console().readPassword();

                if (null != szPwd)
                {
                    strPassword = new String(szPwd);
                }
                else
                {
                    System.out.println("Invalid login credentials.");
                    break;
                }
                
                strDataFile = args[4];
            }
			
			try
			{	                
				m_dbConn = new DatabaseConnector(strDBServer, nPort, strUser, strPassword);
				m_dbConn.connect();
				
				// Drop old tables
                if(ImportType.Reset == impType)
                {
                    m_dbConn.dropTable("characters");
                    m_dbConn.dropTable("inventory");
                    m_dbConn.dropTable("quest_status");
                    m_dbConn.dropTable("quest_solutions");
                }
                
				m_dbConn.dropTable("rooms");
				m_dbConn.dropTable("action_results");
				m_dbConn.dropTable("actions");		
				m_dbConn.dropTable("items");
				m_dbConn.dropTable("moves");
				m_dbConn.dropTable("npcs");
				m_dbConn.dropTable("objects");				
				m_dbConn.dropTable("quests");				
				m_dbConn.dropTable("quest_steps");
                m_dbConn.dropTable("monsters");
                m_dbConn.dropTable("monster_locs");
                m_dbConn.dropTable("loot_table");
				
                if(ImportType.Reset == impType)
                {
                    m_dbConn.addTable("CREATE TABLE characters ( username VARCHAR(32) NOT NULL, pwd_hash VARBINARY(100) NOT NULL, " +
                                "pwd_salt VARBINARY(16) NOT NULL, created DATE NOT NULL, description VARCHAR(1000), char_type INT NOT NULL," +
                                "xp INT, gold INT, health INT NOT NULL, max_health INT NOT NULL, attack_power INT NOT NULL, " +
                                "magic_power INT NOT NULL, defense INT NOT NULL, magic_defense INT NOT NULL, last_room INT NOT NULL, " +
                                "PRIMARY KEY ( username ));");
                    
                    m_dbConn.addTable("CREATE TABLE inventory (ItemID INT NOT NULL, username VARCHAR(32) NOT NULL,"
                                  + "count INT NOT NULL, " +
									" PRIMARY KEY ( ItemID, username));");
                    
                    m_dbConn.addTable("CREATE TABLE quest_status (username VARCHAR(30) NOT NULL," +
								  "quest_id INT NOT NULL, step INT NOT NULL, completed INT NOT NULL, " +
								  "PRIMARY KEY (username, quest_id));");
                    
                    m_dbConn.addTable("CREATE TABLE quest_solutions (quest_id INT NOT NULL, "
                            + "username VARCHAR(32) NOT NULL,"
                            + "event_type INT NOT NULL, name VARCHAR(32) NOT NULL,"
                            + "PRIMARY KEY (quest_id, username, event_type, name));");
                }
                
				// Build database tables
				m_dbConn.addTable("CREATE TABLE rooms (ID INT NOT NULL," +
						"name VARCHAR(30) NOT NULL,description VARCHAR(2000) NOT NULL, " +
						"PRIMARY KEY ( id ));");				
				
				m_dbConn.addTable("CREATE TABLE moves (RoomID INT NOT NULL, direction VARCHAR(20) NOT NULL," +
						"NextRoomID INT NOT NULL, description VARCHAR(100) NOT NULL," +
						"PRIMARY KEY ( RoomID, direction ));");
				
				
				m_dbConn.addTable("CREATE TABLE npcs (ID INT NOT NULL, room INT NOT NULL, name VARCHAR(30) NOT NULL, " +
						"description VARCHAR(2000) NOT NULL, intro VARCHAR(1000) NOT NULL, " +
						"PRIMARY KEY ( ID ) );");
				
				m_dbConn.addTable("CREATE TABLE actions ( ID INT NOT NULL, parent INT NOT NULL, " +
						"name VARCHAR(50) NOT NULL, result INT NOT NULL, " +
						"quest_dependency_id INT, quest_dependency_step INT," +
                        "quest_dependency_complete INT, " +
						"PRIMARY KEY ( ID ) );");
				
				m_dbConn.addTable("CREATE TABLE objects ( ID INT NOT NULL, room INT NOT NULL, name VARCHAR(50) NOT NULL, " +
						"description VARCHAR(2000) NOT NULL, PRIMARY KEY ( ID ) );");
				
				m_dbConn.addTable("CREATE TABLE action_results ( ID INT NOT NULL, parent INT NOT NULL, " +
						"Type VARCHAR(20) NOT NULL, description VARCHAR(2000), " +
						"ItemID INT, Value INT, PRIMARY KEY ( ID ) );");
				
				m_dbConn.addTable("CREATE TABLE items ( ID INT NOT NULL, name VARCHAR(50) NOT NULL, " +
						"description VARCHAR(1000) NOT NULL, weapon INT NOT NULL, stackable INT NOT NULL, "
                        + "delete_on_use INT NOT NULL, damage_type VARCHAR(10) NOT NULL, damage INT NOT NULL, "
                        + "effect VARCHAR(20) NOT NULL, req_room_id INT NOT NULL, value INT NOT NULL, "
                        + "effect_text VARCHAR(2000) NOT NULL, quest_dependency_id INT NOT NULL, "
                        + "quest_dependency_step INT NOT NULL, update_quest_step INT NOT NULL, "
                        + "PRIMARY KEY ( ID ));");								
                
                m_dbConn.addTable("CREATE TABLE monsters ( ID INT NOT NULL, name VARCHAR(32) NOT NULL, " +
                        "description VARCHAR(1000) NOT NULL, " +
						"health INT NOT NULL, max_health INT NOT NULL, attack_power INT NOT NULL, magic_power INT NOT NULL," +
                        "defense INT NOT NULL, magic_defense INT NOT NULL, loot_table_id INT NOT NULL, " +
                        "kill_xp INT NOT NULL, kill_gold INT NOT NULL, respawn_timer INT NOT NULL, update_quest_id INT NOT NULL," +
                        "update_quest_step INT NOT NULL, " +
                        "PRIMARY KEY ( ID ));");
								                
                m_dbConn.addTable("CREATE TABLE loot_table (table_id INT NOT NULL, item_id INT NOT NULL, drop_percent INT NOT NULL, " +
									" PRIMARY KEY ( table_id, item_id ));");
				
				m_dbConn.addTable("CREATE TABLE quests (ID INT NOT NULL, name VARCHAR(100) NOT NULL," +
								  "first_completion_user VARCHAR(30), reward_gold INT NOT NULL," +
								  "reward_xp INT NOT NULL, reward_item INT, first_bonus INT NOT NULL," +
								  " PRIMARY KEY (ID));");
												
				m_dbConn.addTable("CREATE TABLE quest_steps (quest_id INT NOT NULL, step_number INT NOT NULL," +
								  "description VARCHAR(1000) NOT NULL, hint VARCHAR(200) NOT NULL," +
								  "reward_gold INT, reward_xp INT NOT NULL, reward_item INT," +
								  "PRIMARY KEY (quest_id, step_number));");
                
                m_dbConn.addTable("CREATE TABLE monster_locs (monster_id INT NOT NULL, room_id INT NOT NULL);");
				
				// Import world data from XML files
				WorldImporter worldImp = new WorldImporter(strDataFile, m_dbConn);
				
				worldImp.importData();
				
				m_dbConn.disconnect();
			}
			catch(Exception e)
			{
				System.out.println("Exception in main(): " + e);
			}
			
			break;
		}
	}

}
