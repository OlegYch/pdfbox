/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pdfbox.util.operator;

import java.util.List;

import org.apache.pdfbox.pdmodel.graphics.color.*;
import org.apache.pdfbox.util.PDFOperator;
import java.io.IOException;

/**
 * <p>Set the stroking color space.</p>
 * 
 * @author <a href="mailto:andreas@lehmi.de">Andreas Lehmkühler</a>
 * @version $Revision: 1.0 $
 */
public class SetStrokingColor extends OperatorProcessor 
{
    /**
     * SC,SCN Set color space for stroking operations.
     * @param operator The operator that is being executed.
     * @param arguments List
     * @throws IOException If an error occurs while processing the font.
     */
    public void process(PDFOperator operator, List arguments) throws IOException
    {
    	PDColorSpace colorSpace = context.getGraphicsState().getStrokingColorSpace().getColorSpace();
    	if (colorSpace != null) 
    	{
	    	OperatorProcessor newOperator = null;
	    	if (colorSpace instanceof PDDeviceGray) 
	    	    newOperator = new SetStrokingGrayColor();
	    	else if (colorSpace instanceof PDDeviceRGB)
	    	    newOperator = new SetStrokingRGBColor();
	    	else if (colorSpace instanceof PDDeviceCMYK)
	   	    	newOperator = new SetStrokingCMYKColor();
	    	else if (colorSpace instanceof PDICCBased)
		    	newOperator = new SetStrokingICCBasedColor();
	    	if (newOperator != null) 
	    	{
	    		newOperator.setContext(getContext());
	    		newOperator.process(operator, arguments);
	    	}
	    	else
	    		logger().info("Not supported colorspace "+colorSpace.getName() + " within operator "+operator.getOperation());
    	}
    	else
    		logger().warning("Colorspace not found in "+getClass().getName()+".process!!");

    }
   
}