package com.iffi;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonConverter {
	
		public void jsonAssets(List<Object> Assets) {
		//calls the .json converter 
		Gson gson = new GsonBuilder().setPrettyPrinting().create();{
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("data/Assets.json"));
			gson.toJson(Assets, writer);
			writer.close();
		} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		public void jsonPersons(List<Object> personsList) {
			//calls the .json converter 
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			try {
				Writer writer = Files.newBufferedWriter(Paths.get("data/Persons.json"));
				gson.toJson(personsList, writer);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

}