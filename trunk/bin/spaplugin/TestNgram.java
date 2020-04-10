package org.processmining.plugins;

import java.util.ArrayList;
import java.util.List;

import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;




public class TestNgram {
	@Plugin(
	        name = "Its working Ngram", 
	        parameterLabels = {"XLog"}, 
	        returnLabels = { "Outcome String from XLog"}, 
	        returnTypes = { String.class}, 
	        userAccessible = true, 
	        help = "Produces the string: 'Hello Lokesh'"
	)
	@UITopiaVariant(
	        affiliation = "Is it visible", 
	        author = "LK + JB + FS", 
	        email = "My e-mail address"
	)

        
        
        public static String ngrampredict(PluginContext context, XLog xlog){
		ArrayList<String> x = (ArrayList<String>) getAttributesFromLog(xlog);
		String str = convertToString(x);
		//System.out.println(str);
		return str;
 
    }
		
	  private static List<String> getAttributesFromLog(XLog xLog){
		  ArrayList<String> list = new ArrayList<String>();
		  List<String> labels = new ArrayList<>();
		    for (XTrace t : xLog) {
		        //System.out.println(t);
		        labels = getTraceLabels(t);
		        list.addAll(labels);
		        //System.out.println(labels);
		        
		      }
		    System.out.println(list);
		   return list;
		  //return new ArrayList<>(xLog.stream().findFirst().get().stream().findFirst().get().getAttributes().keySet());
		    
		  }
	  
	  private static List<String> getTraceLabels(XTrace trace) {
		    //List<String> attributeMappings = settingsUi.getSettings().getClassifierTypes();
		    List<String> labels = new ArrayList<>();
		    
		    for (XEvent e : trace) {
		      StringBuilder val = new StringBuilder();
		      for (String k : e.getAttributes().keySet()) {
		        
		          val.append(e.getAttributes().get(k)).append("+");
		      }
		      labels.add(val.toString());
		    }
		    return labels;
		  }
	  
	  private static String convertToString(ArrayList<String> al) {
		  StringBuffer sb = new StringBuffer();
	      for (String s : al) {
	         sb.append(s);
	         sb.append(" ");
	      }
	      String str = sb.toString();
	      return str;
	  }
        


}
	
