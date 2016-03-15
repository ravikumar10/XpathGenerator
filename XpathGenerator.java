import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XpathGenerator extends DefaultHandler {

    private String xPath = "/";
    private XMLReader xmlReader;
    private XpathGenerator parent;
    private StringBuilder characters = new StringBuilder();
    private Map<String, Integer> elementNameCount = new HashMap<String, Integer>();

    public XpathGenerator(XMLReader xmlReader) {
        this.xmlReader = xmlReader;
    }

    private XpathGenerator(String xPath, XMLReader xmlReader, XpathGenerator parent) {
        this(xmlReader);
        this.xPath = xPath;
        this.parent = parent;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        Integer count = elementNameCount.get(qName);
        if(qName.contains(":")){
               	qName="*[local-name()='"+qName.split(":")[1]+"']";
        }
        if(null == count) {
            count = 1;
        } else {
            count++;
        }
        elementNameCount.put(qName, count);
        String childXPath = xPath + "/" + qName + "[" + count + "]";

        int attsLength = atts.getLength();
        for(int x=0; x<attsLength; x++) {
            System.out.println(childXPath + "[@" + atts.getQName(x) + "='" + atts.getValue(x) + "']");
        }

        XpathGenerator child = new XpathGenerator(childXPath, xmlReader, this);
        xmlReader.setContentHandler(child);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String value = characters.toString().trim();
        if(value.length() > 0) {
            System.out.println(xPath + "/text()='" + characters.toString() + "'");
        }
        xmlReader.setContentHandler(parent);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        characters.append(ch, start, length);
    }


	public static void main(String[] args)  {
	 	String filename = null;
	    String type = null;
	    	    
	    SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = null;
		try {
			sp = spf.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        XMLReader xr = null;
		try {
			xr = sp.getXMLReader();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        xr.setContentHandler(new XpathGenerator(xr));
	    
	    for(int i=0;i<args.length;i++)
	    {
	        String arg = args[i];
	        if(arg.equalsIgnoreCase("-xml"))
	        {
	            if((args.length-i)>0)
	            	filename = args[++i];
	            type="XML";
	        }
	        else if(arg.equalsIgnoreCase("-soap"))
            {
	            if((args.length-i)>0)
	            	filename = args[++i];
	            type="SOAP";
            }
	        else if(arg.equalsIgnoreCase("-json"))
            {
	            if((args.length-i)>0)
	            	filename = args[++i];
	            type="JSON";
            }
	      
	    }
	    if(filename !=null )
	    {
	    	if(type.equalsIgnoreCase("XML")){
				
	    		
	    		   try {
	    				xr.parse(new InputSource(new FileInputStream(filename)));
	    			} catch (FileNotFoundException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			} catch (IOException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			} catch (SAXException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
			
			}
			if(type.equalsIgnoreCase("SOAP")){
				

	    		   try {
	    				xr.parse(new InputSource(getFileContent(filename)));
	    			} catch (FileNotFoundException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			} catch (IOException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			} catch (SAXException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
				
			}
			if(type.equalsIgnoreCase("JSON")){
				
				String xml=null;
				StringBuffer xmleading = new StringBuffer("<?xml version=\'1.0\' encoding=\'UTF-8\'?>");
				JSONObject json = null;
				try {
					json = new JSONObject(getFileContent(filename));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					xml = xmleading.append(org.json.XML.toString(json)).toString();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				PrintWriter writer = null;
				try {
					writer = new PrintWriter("sample.xml", "UTF-8");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				writer.println(xml);
				
				writer.close();
				
				 try {
						xr.parse(new InputSource(new FileInputStream("sample.xml")));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 boolean success = (new File ("sample.xml")).delete();
			
			}
	        
	    }
	    
 }

static String getFileContent(final String dataFilename){
	 StringBuffer messg = new StringBuffer();
	 BufferedReader br = null;
	 try {
        br = new BufferedReader(new FileReader(dataFilename));
	 } catch (FileNotFoundException e1) {
        e1.printStackTrace();
    }
    try 
    {
        String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null) {
                messg.append(sCurrentLine);
        }
    } catch (IOException e) {
        e.printStackTrace();
    } 
  
	return messg.toString();
}

}