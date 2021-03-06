// Copyright (C),2005-2008 HandCoded Software Ltd.
// All rights reserved.
//
// This software is licensed in accordance with the terms of the 'Open Source
// License (OSL) Version 3.0'. Please see 'license.txt' for the details.
//
// HANDCODED SOFTWARE LTD MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
// SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT
// LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
// PARTICULAR PURPOSE, OR NON-INFRINGEMENT. HANDCODED SOFTWARE LTD SHALL NOT BE
// LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
// OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.

package com.handcoded.fpml.validation;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.handcoded.validation.Rule;
import com.handcoded.validation.RuleSet;
import com.handcoded.validation.ValidationErrorHandler;
import com.handcoded.xml.DOM;
import com.handcoded.xml.NodeIndex;
import com.handcoded.xml.XPath;

/**
 * The <CODE>EqdRules</CODE> class contains a <CODE>RuleSet</CODE>
 * initialised with FpML defined validation rules for equity derivative
 * products.
 *
 * @author	BitWise
 * @version	$Id$
 * @since	TFP 1.0
 */
public final class EqdRules extends FpMLRuleSet
{
	/**
	 * A <CODE>Rule</CODE> instance that ensures the unadjusted expiration
	 * date is after the trade date for american options.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE02	= new Rule (Preconditions.R4_0__LATER, "eqd-2")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName("equityAmericanExercise"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	expiration	= XPath.path (context, "expirationDate", "adjustableDate", "unadjustedDate");
					Element	trade		= XPath.path (context, "..", "..", "..", "tradeHeader", "tradeDate");

					if ((expiration == null) || (trade == null) || greaterOrEqual (toDate (expiration), toDate (trade)))
						continue;

					errorHandler.error ("305", context,
						"American exercise expiration date " + toToken (expiration) +
						" should be the same or later than trade date " + toToken (trade),
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the latest exercise time is
	 * after the earliest exercise time for american options.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE03	= new Rule (Preconditions.R4_0__LATER, "eqd-3")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("equityAmericanExercise"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength(); ++index) {
					Element context	= (Element) list.item (index);

					if (implies (
							equal (XPath.path (context, "latestExerciseTimeType"), "SpecificTime"),
							exists (XPath.path (context, "latestExerciseTime"))))
						continue;

					errorHandler.error ("305", context,
						"American exercise structure should include a latest " +
						"exercise time, since time type is set to SpecificTime",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the unadjusted commencement
	 * date is the same as the trade date for bermudan options.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE04	= new Rule (Preconditions.R4_0__LATER, "eqd-4")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("equityBermudaExercise"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	commence	= XPath.path (context, "commencementDate", "adjustableDate", "unadjustedDate");
					Element	trade		= XPath.path (context, "..", "..", "..", "tradeHeader", "tradeDate");

					if ((commence == null) || (trade == null) || greaterOrEqual (toDate (commence), toDate (trade)))
						continue;

					errorHandler.error ("305", context,
						"Bermuda exercise commencement date " + toToken (commence) +
						" should not be before the trade date " + toToken (trade),
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the unadjusted expiration
	 * date is after the trade date for bermudan options.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE05	= new Rule (Preconditions.R4_0__LATER, "eqd-5")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("equityBermudaExercise"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	expiration	= XPath.path (context, "expirationDate", "adjustableDate", "unadjustedDate");
					Element	trade		= XPath.path (context, "..", "..", "..", "tradeHeader", "tradeDate");

					if ((expiration == null) || (trade == null) || greaterOrEqual (toDate (expiration), toDate (trade)))
						continue;

					errorHandler.error ("305", context,
						"Bermuda exercise expiration date " + toToken (expiration) +
						" should not be before the trade date " + toToken (trade),
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the latest exercise time is
	 * after the earliest exercise time for bermudan options.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE06	= new Rule (Preconditions.R4_0__LATER, "eqd-6")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("equityBermudaExercise"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context	= (Element) list.item (index);

					if (implies (
							equal (XPath.path (context, "latestExerciseTimeType"), "SpecificTime"),
							exists (XPath.path (context, "latestExerciseTime"))))
						continue;

					errorHandler.error ("305", context,
						"Bermuda exercise structure should include a latest " +
						"exercise time, since time type is set to SpecificTime",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures bermudan exercise dates are
	 * in order.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 * @deprecated
	 */
	public static final Rule	RULE07	= new Rule (Preconditions.R4_0__LATER, "eqd-7")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("equityBermudaExercise"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				list = XPath.paths (list, "bermudaExerciseDates", "date");
				for (int index = 0; index < list.getLength (); ++index) {
					Element 	context = (Element) list.item (index);
					Element		next	= DOM.getNextSibling (context);

					if ((next == null) || less (toDate (context), toDate (next)))
						continue;

					errorHandler.error ("305", context,
						"Bermuda exercise dates " + toToken (context) + " and " +
						toToken (next) + " are not in order",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures bermudan exercise dates are
	 * after commencement.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE08	= new Rule (Preconditions.R4_0__LATER, "eqd-8")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("equityBermudaExercise"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				list = XPath.paths (list, "bermudaExerciseDates", "date");
				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	commence	= XPath.path (context, "..", "..", "commencementDate", "adjustableDate", "unadjustedDate");

					if ((commence == null) || greater (toDate (context), toDate (commence)))
						continue;

					errorHandler.error ("305", context,
						"Bermuda exercise date " + toToken (context) +
						" should be after exercise period commencement date " +
						toToken (commence),
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures bermudan exercise dates are
	 * before expiry.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE09	= new Rule (Preconditions.R4_0__LATER, "eqd-9")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("equityBermudaExercise"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				list = XPath.paths (list, "bermudaExerciseDates", "date");
				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	expiration	= XPath.path (context, "..", "..", "expirationDate", "adjustableDate", "unadjustedDate");

					if ((expiration == null) || lessOrEqual (toDate (context), toDate (expiration)))
						continue;

					errorHandler.error ("305", context,
						"Bermuda exercise date " + toToken (context) +
						" should be on or before exercise period expiration date " +
						toToken (expiration),
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures bermudan exercise dates
	 * are unique.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE10	= new Rule (Preconditions.R4_0__LATER, "eqd-10")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("equityBermudaExercise"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				list = XPath.paths (list, "bermudaExerciseDates", "date");
				for (int index = 0; index < list.getLength (); ++index) {
					Element context	= (Element) list.item (index);
					Element	other	= DOM.getNextSibling (context);

					for (; other != null; other = DOM.getNextSibling (other)) {
						if (notEqual (toDate (context), toDate (other))) continue;

						errorHandler.error ("305", context,
							"Duplicate bermuda exercise date, " + toToken (other),
							getName (), toToken (other));

						result = false;
					}
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the unadjusted expiration
	 * date is after the trade date for bermudan options.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE12	= new Rule (Preconditions.R4_0__LATER, "eqd-12")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("equityEuropeanExercise"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	expiration	= XPath.path (context, "expirationDate", "adjustableDate", "unadjustedDate");
					Element	trade		= XPath.path (context, "..", "..", "..", "tradeHeader", "tradeDate");

					if ((expiration == null) || (trade == null) || greaterOrEqual (toDate (expiration), toDate (trade)))
						continue;

					errorHandler.error ("305", context,
						"European exercise expiration date " + toToken (expiration) +
						" should not be before the trade date " + toToken (trade),
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures equity option premium payment
	 * date is on or after the trade date.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE13	= new Rule (Preconditions.R4_0__LATER, "eqd-13")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("trade"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	premiumDate	= XPath.path (context, "equityOption", "equityPremium", "paymentDate", "unadjustedDate");
					Element	tradeDate	= XPath.path (context, "tradeHeader", "tradeDate");

					if ((premiumDate == null) || (tradeDate == null) || greaterOrEqual (toDate (premiumDate), toDate (tradeDate)))
						continue;

					errorHandler.error ("305", context,
						"Equity premium payment date " + toToken (premiumDate) +
						" must be on or after trade date " + toToken (tradeDate),
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures broker equity option premium
	 * payment date is on or after the trade date.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE14	= new Rule (Preconditions.R4_0__LATER, "eqd-14")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("trade"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	premiumDate	= XPath.path (context, "brokerEquityOption", "equityPremium", "paymentDate", "unadjustedDate");
					Element	tradeDate	= XPath.path (context, "tradeHeader", "tradeDate");

					if ((premiumDate == null) || (tradeDate == null) || greaterOrEqual (toDate (premiumDate), toDate (tradeDate)))
						continue;

					errorHandler.error ("305", context,
						"Broker equity premium payment date " + toToken (premiumDate) +
						" must be on or after trade date " + toToken (tradeDate),
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures European option valuation
	 * date is the same as the expiration date.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE15	= new Rule (Preconditions.R4_0__LATER, "eqd-15")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("equityExercise"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	valuationDate	= XPath.path (context, "equityValuation", "valuationDate", "adjustableDate", "unadjustedDate");
					Element	expirationDate	= XPath.path (context, "equityEuropeanExercise", "expirationDate", "adjustableDate", "unadjustedDate");

					if ((valuationDate == null) || (expirationDate == null) || equal (toDate (valuationDate), toDate (expirationDate)))
						continue;

					errorHandler.error ("305", context,
						"The valuation and expiration dates for a European option must be same",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the number of options in
	 * a multiple exercise American option is correct.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE17	= new Rule (Preconditions.R4_0__LATER, "eqd-17")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (
					  validate (nodeIndex.getElementsByName ("equityOption"), errorHandler)
					& validate (nodeIndex.getElementsByName ("brokerEquityOption"), errorHandler)
					& validate (nodeIndex.getElementsByName ("equityOptionTransactionSupplement"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	multiple	= XPath.path (context, "equityExercise", "equityAmericanExercise", "equityMultipleExercise");
					Element	number		= XPath.path (context, "numberOfOptions");

					if ((multiple == null) || (number == null)) continue;

					Element	integral	= XPath.path (multiple, "integralMultipleExercise");
					Element	maximum		= XPath.path (multiple, "maximumNumberOfOptions");

					if ((integral == null) || (maximum == null) || greaterOrEqual (toDecimal (integral).multiply(toDecimal (maximum)), toDecimal (number)))
						continue;

					errorHandler.error ("305", context,
						"maximumNumberOfOptions * integralMultipleExercise should " +
						"not be greater than numberOfOptions",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the number of options in
	 * a multiple exercise Bermudan option is correct.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE18	= new Rule (Preconditions.R4_0__LATER, "eqd-18")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (
						    validate (nodeIndex.getElementsByName ("equityOption"), errorHandler)
						  & validate (nodeIndex.getElementsByName ("brokerEquityOption"), errorHandler)
						  & validate (nodeIndex.getElementsByName ("equityOptionTransactionSupplement"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	multiple	= XPath.path (context, "equityExercise", "equityBermudaExercise", "equityMultipleExercise");
					Element	number		= XPath.path (context, "numberOfOptions");

					if ((multiple == null) || (number == null)) continue;

					Element	integral	= XPath.path (multiple, "integralMultipleExercise");
					Element	maximum		= XPath.path (multiple, "maximumNumberOfOptions");

					if ((integral == null) || (maximum == null) || lessOrEqual (toDecimal (integral).multiply(toDecimal (maximum)), toDecimal (number)))
						continue;

					errorHandler.error ("305", context,
						"maximumNumberOfOptions * integralMultipleExercise should " +
						"not be greater than numberOfOptions",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures premium is the correct
	 * percentage of notional.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE19	= new Rule (Preconditions.R4_0__LATER, "eqd-19")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (
					    validate (nodeIndex.getElementsByName ("equityOption"), errorHandler)
					  & validate (nodeIndex.getElementsByName ("brokerEquityOption"), errorHandler)
					  & validate (nodeIndex.getElementsByName ("equityOptionTransactionSupplement"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	notionalCcy	= XPath.path (context, "notional", "currency");
					Element	paymentCcy	= XPath.path (context, "equityPremium", "paymentAmount", "currency");

					if ((notionalCcy == null) || (paymentCcy == null) || !isSameCurrency (notionalCcy, paymentCcy)) continue;

					Element	totalValue	= XPath.path (context, "notional", "amount");
					Element	percentage	= XPath.path (context, "equityPremium", "percentageOfNotional");
					Element	amount		= XPath.path (context, "equityPremium", "paymentAmount", "amount");

					if ((totalValue == null) || (percentage == null) || (amount == null) ||
						equal (round (toDecimal (amount), 2), round (toDecimal (totalValue).multiply (toDecimal (percentage)), 2)))
						continue;

					errorHandler.error ("305", context,
						"The equity premium amount does not agree with the figures given for " +
						"the notional and premium percentage",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the payment amount is correct.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE20	= new Rule (Preconditions.R4_0__LATER, "eqd-20")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (
					  validate (nodeIndex.getElementsByName ("equityOption"), errorHandler)
					& validate (nodeIndex.getElementsByName ("brokerEquityOption"), errorHandler)
					& validate (nodeIndex.getElementsByName ("equityOptionTransactionSupplement"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);
					Element	priceCcy	= XPath.path (context, "equityPremium", "pricePerOption", "currency");
					Element	paymentCcy	= XPath.path (context, "equityPremium", "paymentAmount", "currency");

					if ((priceCcy == null) || (paymentCcy == null) || !isSameCurrency (priceCcy, paymentCcy)) continue;

					Element	number		= XPath.path (context, "numberOfOptions");
					Element	entitlement	= XPath.path (context, "optionEntitlement");
					Element	priceEach	= XPath.path (context, "equityPremium", "pricePerOption", "amount");
					Element	amount		= XPath.path (context, "equityPremium", "paymentAmount", "amount");

					if ((number == null) || (entitlement == null) || (priceEach == null) || (amount == null) ||
						equal (round (toDecimal (amount), 2), round (toDecimal (priceEach).multiply (toDecimal (number)).multiply (toDecimal (entitlement)), 2)))
						continue;

					errorHandler.error ("305", context,
						"The equity premium amount does not agree with the figures given for " +
						"the number of options, price per option and entitlement",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the calculationAgentPartyReference
	 * is present.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 * @deprecated
	 */
	public static final Rule	RULE21	= new Rule (Preconditions.R4_0__LATER, "eqd-21")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("calculationAgent"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 	= (Element) list.item (index);

					if (!context.getParentNode().getLocalName ().equals ("trade")) continue;

					if (exists (
							XPath.path (context, "calculationAgentPartyReference")))
						continue;

					errorHandler.error ("305", context,
						"Calculation agent field must contain a calculation agent party reference",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the buyerPartyReference is
	 * different from the SellerPartyReference.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE22	= new Rule (Preconditions.R4_0__LATER, "eqd-22")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (
					  validate (nodeIndex.getElementsByName ("equityOption"), errorHandler)
					& validate (nodeIndex.getElementsByName ("brokerEquityOption"), errorHandler)
					& validate (nodeIndex.getElementsByName ("equityForward"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context	= (Element) list.item (index);
					Element	buyer	= XPath.path (context, "buyerPartyReference");
					Element	seller	= XPath.path (context, "sellerPartyReference");

					if ((buyer == null) || (seller == null) ||
						notEqual (buyer.getAttribute ("href"), seller.getAttribute ("href")))
						continue;

					errorHandler.error ("305", context,
						"The buyerPartyReference/@href must not be the same as the " +
						"sellerPartyReference/@href",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the effective date is the
	 * same or later than the trade date.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE23	= new Rule (Preconditions.R4_0__LATER, "eqd-23")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (
					  validate (nodeIndex.getElementsByName ("equityOption"), errorHandler)
					& validate (nodeIndex.getElementsByName ("brokerEquityOption"), errorHandler)
					& validate (nodeIndex.getElementsByName ("equityForward"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context 		= (Element) list.item (index);
					Element	effectiveDate	= XPath.path (context, "equityEffectiveDate");
					Element	tradeDate		= XPath.path (context, "..", "tradeHeader", "tradeDate");

					if ((effectiveDate == null) || (tradeDate == null) ||
							greaterOrEqual (effectiveDate, tradeDate))
						continue;

					errorHandler.error ("305", context,
						"The equity effective date must be on or after the trade date",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the end date is the same or after
	 * the start date.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE24	= new Rule (Preconditions.R4_0__LATER, "eqd-24")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("schedule"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context		= (Element) list.item (index);
					Element	startDate	= XPath.path (context, "startDate");
					Element	endDate		= XPath.path (context, "endDate");

					if ((startDate == null) || (endDate == null) || lessOrEqual (toDate (startDate), toDate (endDate)))
						continue;

					errorHandler.error ("305", context,
						"The equity schedule start date can not be after the end date",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * A <CODE>Rule</CODE> instance that ensures the payment amount is calculated
	 * correctly.
	 * <P>
	 * Applies to FpML 4.0 and later.
	 * @since	TFP 1.0
	 */
	public static final Rule	RULE25	= new Rule (Preconditions.R4_0__LATER, "eqd-25")
		{
			/**
			 * {@inheritDoc}
			 */
			public boolean validate (NodeIndex nodeIndex, ValidationErrorHandler errorHandler)
			{
				return (validate (nodeIndex.getElementsByName ("brokerEquityOption"), errorHandler));
			}

			private boolean validate (NodeList list, ValidationErrorHandler errorHandler)
			{
				boolean		result	= true;

				for (int index = 0; index < list.getLength (); ++index) {
					Element context		= (Element) list.item (index);
					Element	priceCcy	= XPath.path (context, "equityPremium", "pricePerOption");
					Element	paymentCcy	= XPath.path (context, "equityPremium", "paymentAmount");

					if ((priceCcy == null) || (paymentCcy == null) || !isSameCurrency (priceCcy, paymentCcy)) continue;

					Element	number		= XPath.path (context, "numberOfOptions");
					Element	priceEach	= XPath.path (context, "equityPremium", "pricePerOption", "amount");
					Element	amount		= XPath.path (context, "equityPremium", "paymentAmount", "amount");

					if ((number == null) || (priceEach == null) || (amount == null) ||
						equal (round (toDecimal (amount), 2), round (toDecimal (priceEach).multiply (toDecimal (number)), 2)))
						continue;

					errorHandler.error ("305", context,
						"The equity premium amount does not agree with the figures given for " +
						"the number of options and price per option",
						getName (), null);

					result = false;
				}
				return (result);
			}
		};

	/**
	 * Provides access to the EQD validation rule set.
	 *
	 * @return	The EQD validation rule set.
	 * @since	TFP 1.0
	 */
	public static RuleSet getRules ()
	{
		return (rules);
	}

	/**
	 * A <CODE>RuleSet</CODE> containing all the standard FpML defined
	 * validation rules for equity derivative products.
	 * @since	TFP 1.0
	 */
	private static final RuleSet	rules = RuleSet.forName ("EqdRules");

	/**
	 * Ensures no instances can be created.
	 * @since	TFP 1.0
	 */
	private EqdRules ()
	{ }
}
