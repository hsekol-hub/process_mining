package spaplugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;

import javafx.util.Pair;

public class NgramPredictor {
	@Plugin(
	        name = "Ngram Predictor", 
	        parameterLabels = {"Process Log XES", "Ngram XES"}, 
	        returnLabels = { "Probabilities for next possible activities."}, 
	        returnTypes = {String.class}, 
	        userAccessible = true, 
	        help = "Import process log as XES and an instance/ngram as XES. "
	        		+ "Output are the probabilities of the next possible activities."
	)
	@UITopiaVariant(
	        affiliation = "Smart Process Analytics WT2020", 
	        author = "JB, LK, FS", 
	        email = "@uni-koblenz.de"
	)
	
	// Main method which executes the plugin and returns the final output.
	public static String ngrampredict(PluginContext context, XLog xlog, XLog xinstance){
		// Convert Log to List of Activities
		List<String> log = convXEStoList(xlog);
		// Convert Instance to List
		List<String> instance = convXEStoList(xinstance);
		// Counts the frequency of the following activities of the instance
		Map<String, Integer> frequencies = countFrequencies(log, instance);
		// Counts all occurences of the given instance in the given process log.
		int allocc = countAllOccurences(frequencies);
		// Create list of the five next activities with the highest probability.
		List<Pair<String, Integer>> maxEntries = new ArrayList<Pair<String, Integer>>();
		for (int idx = 0; idx < 5 && idx < frequencies.size(); idx++) {
			Entry<String, Integer> maxEntry = null;
			for (Entry<String, Integer> entry : frequencies.entrySet()) {
			    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0){
			        maxEntry = entry;
			    }
			}
			maxEntries.add(new Pair<String, Integer> (maxEntry.getKey(), maxEntry.getValue()));
			frequencies.remove(maxEntry.getKey());
		}
		
		// Output in Prom. String is converted into HTML to allow better formatting.
		String str = "<html> Ngram was found " + allocc + " times.<br><br>"
				+ "The five most likely activities are: <br>"
				+ "<table style=\"width:100%\"> <tr><th>Activity</th> <th>Probability</th></tr>";
		for (Pair<String, Integer> entry : maxEntries) {
			float probability = ((float) entry.getValue())/allocc;
			String entrykey = entry.getKey();
			String probabilitystring = String.valueOf(probability);
			str += "<tr><td>" + entrykey + "</td><td>" + probabilitystring + "</td></tr>";
		}
		str += "</table>";
		return str;
	}
	
	// Counts the total number of occurences of the instance/ngram in the process log.
	public static Integer countAllOccurences(Map<String, Integer> freqs) {
		int allocc = 0;
		for (int value : freqs.values()) { 
			allocc += value;
			}
		return allocc;
	}
	
	// Converts the XES file (process log and instance) to a List<String>.
	public static List<String> convXEStoList(XLog xLog){
		ArrayList<String> list = new ArrayList<String>();
		List<String> labels = new ArrayList<>();
		for (XTrace t : xLog) {
		    labels = getTraceLabels(t);
		    list.addAll(labels);
		    }
		return list;
		}
	
	// Reads content of elements in traces and returns labels as list. 
	public static List<String> getTraceLabels(XTrace trace) {
		  List<String> labels = new ArrayList<>();
		  for (XEvent e : trace) {
			  StringBuilder val = new StringBuilder();
		      for (String k : e.getAttributes().keySet()) {
		    	  val.append(e.getAttributes().get(k));
		    	  }
		      labels.add(val.toString());
		    }
		    return labels;
		  }
	
	// 
	public static String convertToString(List<String> comp) {
		StringBuffer sb = new StringBuffer();
	    for (String s : comp) {
	       sb.append(s);
	       sb.append(" ");
	       }
	    String str = sb.toString();
	    return str;
	  }
	
	// Counts the frequencies of every following element of the ngram/instance and returns a HashMap with the element as key and frequency as value.
	public static HashMap<String, Integer> countFrequencies(List<String> log, List<String> instance){
		HashMap<String, Integer> freqs = new HashMap<String, Integer>();
		if (log.size() <= instance.size()){
			return null;
			}
		for(int logidx = 0; logidx < (log.size() - instance.size()); logidx++){
			boolean match = true;
			for(int instidx = 0; instidx < instance.size(); instidx++){
				if (!log.get(logidx + instidx).equals(instance.get(instidx))) {
					match = false;
					break;
				} 
			}
			if (match) {
				int nextTokenidx = logidx + instance.size();
				String nextToken;
				if (nextTokenidx >= log.size()) {
					break;
				} else {
					nextToken = log.get(nextTokenidx);
				}
				if(!freqs.containsKey(nextToken)) {
					freqs.put(nextToken, 1);
				} else {
					freqs.replace(nextToken, freqs.get(nextToken) + 1);
				}
			}
		}
		return freqs;
	  }

}
	