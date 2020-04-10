package org.processmining.contexts.util;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Abstract composite panel class that can return its main component.
 * 
 * @author hverbeek
 *
 */
public abstract class CompositePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4966198250541292556L;

	/**
	 * Return the main component of the composite panel.
	 * @return The main component.
	 */
	public abstract JComponent getMainComponent();

}
