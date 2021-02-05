/**
 *
 */
package tech3g.fk.app.statis.actions;

import org.jfree.chart.JFreeChart;

import tech3g.common.web.ViewBean;

/**
 * @author tech3g
 *
 */
public class Tech3gStatisSpbGraphBean extends ViewBean {

	/**
	 *
	 */
	private static final long serialVersionUID = -6106001630601937666L;


	private JFreeChart chat;


	/**
	 * @return chat
	 */
	public JFreeChart getChat() {
		return chat;
	}


	/**
	 * @param chat セットする chat
	 */
	public void setChat(JFreeChart chat) {
		this.chat = chat;
	}


}
