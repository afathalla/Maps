//package com.boslla.maps.util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.vaadin.sami.autocomplete.AutoCompleteTextField.Suggestion;
//import org.vaadin.sami.autocomplete.AutoCompleteTextField;
//
//import com.boslla.maps.containers.UnitContainer;
//
// public class UnitSuggester implements AutoCompleteTextField.Suggester {
//
//	        public List<Suggestion> getSuggestions(
//	                final AutoCompleteTextField source, final String text,
//	                final int cursorPosition) {
//
//	            // Parse the current word prefix out from the text
//	            String prefix;
//	            if (text != null && cursorPosition <= text.length()) {
//	                // Find the prefix
//	                prefix = text.substring(0, cursorPosition);
//	                int lastWordStart = Math.max(prefix.lastIndexOf(' '), prefix
//	                        .lastIndexOf('\n'));
//	                if (lastWordStart < cursorPosition) {
//	                    prefix = text.substring(lastWordStart + 1, cursorPosition);
//	                } else {
//	                    prefix = "";
//	                }
//	            } else {
//	                prefix = "";
//	            }
//
//	            // Look up matching items
//	            prefix = prefix.toLowerCase();
//	            List<Suggestion> found = new ArrayList<Suggestion>();
//	         
//	    		final UnitContainer unitContainer = UnitContainer.getAllUnits(); 
//	            
//	            for (int i = 0; i < unitContainer.size(); i++) {
//	                String name = unitContainer.getIdByIndex(i).getUnitName();
//	                if (name.toLowerCase().startsWith(prefix)) {
//
//	                    // Value is the same as name without the part in parentheses
//	                    String value = name;
//	                    if (value.indexOf('(') > 0) {
//	                        value = value.substring(0, value.indexOf('(')).trim();
//	                    }
//
//	                    // Append a space to values for convenience.
//	                    value += " ";
//
//	                    // Suffix of the suggestion (the remainder of the matched
//	                    // part). This is used for incremental searching at the
//	                    // client-side
//	                    String suffix = unitContainer.getIdByIndex(i).getUnitName().substring(
//	                            prefix.length());
//
//	                    // Create a new suggestion
//	                    found.add(source.createSuggestion(name, value, suffix,
//	                            cursorPosition - prefix.length(), cursorPosition));
//	                }
//	            }
//
//	            return found;
//	        }
//}
