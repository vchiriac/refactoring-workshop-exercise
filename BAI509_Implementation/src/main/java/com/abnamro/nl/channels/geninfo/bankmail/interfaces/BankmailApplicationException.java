package com.abnamro.nl.channels.geninfo.bankmail.interfaces;

import com.abnamro.nl.channels.geninfo.bankmail.util.Messages;

/**
 * BankmailApplicationException
 *
 * <PRE>
 * <B>History:</B>
 * Developer          Date       Change Reason	  Change
 * ------------------ ---------- ----------------- ----------------------------------------------
 * TCS				29-05-2012	Initial version	  Release 1.0
 * </PRE>
 * @author
 * @see
 */
public class BankmailApplicationException extends BusinessApplicationException {
	/**
	 * Default constructor.
	 *
	 */
	public BankmailApplicationException() {
		super();
	}

	/**
	 * Constructor that will also set messages on the exception.
	 * @param messages Messages
	 */
	public BankmailApplicationException(Messages messages) {
		super(messages);
	}

	/**
	 * Constructor that takes an existing AABException. This will move any
	 * messages into the new exception.
	 * @param aabException AABException
	 */
	public <AABException> BankmailApplicationException(AABException aabException) {
		super((Messages) aabException);
	}

}
