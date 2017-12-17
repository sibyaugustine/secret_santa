package com.devdolphin.games.scretsanta.provider;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Semaphore;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FriendFinder {
	private Path teamResourceFilePath;
	private String teamname;
	private String clientip;
	private static final String resourcefilename = "friendlist.json";
	private static final Semaphore available = new Semaphore(1);
	public FriendFinder(String FilePath,String teamName,String clientip){
		this.teamname = teamName;
		this.clientip = clientip;
		this.teamResourceFilePath = Paths.get(FilePath,teamName,resourcefilename);
	}
	public String getTeamname() {
		return teamname;
	}
	public Path getFilepath() {
		return teamResourceFilePath;
	}
	public String findMyFriend() throws Exception {
		if(!isValidFriendList()) {
			throw new Exception(resourcefilename+" Not Found");
		}
		available.acquire();
		String name = "";
		FileReader reader = new FileReader(teamResourceFilePath.toFile());
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
		JSONArray avaliable= (JSONArray) jsonObject.get("available");
		JSONArray selected= (JSONArray) jsonObject.get("selected");

		if(isAllreadyAssiged(selected)) {
			throw new Exception("Already assigned");
		}

		int rnd = new Random().nextInt(avaliable.size());
		JSONObject selecteditem = (JSONObject) avaliable.get(rnd);
		selecteditem.put("by", clientip);
		avaliable.remove(rnd);
		selected.add(selecteditem);
		FileWriter file = new FileWriter(teamResourceFilePath.toFile());
		file.write(jsonObject.toJSONString());
		file.flush();
		available.release();
		System.out.println("\nJSON Object: " + jsonObject.toJSONString());
		name = (String) selecteditem.get("name");
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	private boolean isValidFriendList() {
		File file = teamResourceFilePath.toFile();
		boolean exists = file.exists();
		return exists;
	}

	private boolean isAllreadyAssiged(JSONArray items) {
		if(items.size()==0) {
			return false;
		}

		Iterator i = items.iterator();
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			String selectedby = (String) innerObj.get("by");
			if(selectedby.equals(clientip)) {
				return true;
			}
		}
		return false;
	}
}
