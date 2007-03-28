// Copyright (C),2005-2006 HandCoded Software Ltd.
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

package com.handcoded.framework.swing.filechooser;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * The <CODE>XmlFileFilter</CODE> is a customised <CODE>FileFilter</CODE>
 * that accepts filenames with a '.XML' suffix.
 * 
 * @author 	BitWise
 * @version	$Id$
 * @since	TFP 1.0
 */
public final class XmlFileFilter extends FileFilter
{
	/**
	 * Constructs a <CODE>XmlFileFilter</CODE> instance.
	 * @since	TFP 1.0
	 */
	public XmlFileFilter ()
	{ }
	
	/**
	 * Implements a filter that only allows directories and files with a
	 * '.xml' suffix to be accepted.
	 * 
	 * @param	file			The <CODE>File</CODE> to be filtered.
	 * @return	<CODE>true</CODE> if the <CODE>File</CODE> was a directory
	 * 			or XML file.
	 * @since	TFP 1.0
	 */
	public boolean accept (File file)
	{
		return (file.isDirectory () || file.getName().endsWith (".xml"));
	}

	/**
	 * Returns a description of the filter that is displayed in any
	 * <CODE>javax.swing.JFileChooser</CODE> which uses it.
	 * 
	 * @return	A brief textual description of the filter.
	 * @since	TFP 1.0
	 */
	public String getDescription ()
	{
		return ("XML Files (*.xml)");
	}
}