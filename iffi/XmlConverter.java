package com.iffi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlConverter {
	
		public void xmlAssets(List<Object> AllAssets) {
			//calls the .xml converter
			XStream xstream = new XStream(new DomDriver());
			
			xstream.alias("Property", Property.class);
			xstream.alias("Stock", Stock.class);
			xstream.alias("Crypto", Crypto.class);
			xstream.alias("Assets", List.class);
			
			try {
				xstream.toXML(AllAssets, new FileWriter("data/Assets.xml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void xmlPersons(List<Object> AllPersons) {
			//calls the .xml converter
			XStream xstream = new XStream(new DomDriver());
			
			xstream.alias("PersonsList", List.class);
			
		
			try {
				xstream.toXML(AllPersons, new FileWriter("data/Persons.xml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}

