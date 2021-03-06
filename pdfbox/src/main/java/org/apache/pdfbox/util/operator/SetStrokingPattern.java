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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.graphics.pattern.PDPatternResources;
import org.apache.pdfbox.util.PDFOperator;

/**
 * <p>Set the stroking pattern.</p>
 *
 * @version $Revision: 1.0 $
 */
public class SetStrokingPattern extends OperatorProcessor
{
    /**
     * Set pattern instead of a color space for stroking operations.
     * @param operator The operator that is being executed.
     * @param arguments List
     * @throws IOException If an error occurs while processing the pattern.
     */
    public void process(PDFOperator operator, List<COSBase> arguments) throws IOException
    {
        COSName selectedPattern = (COSName)arguments.get(0);
        Map<String,PDPatternResources> patterns = getContext().getResources().getPatterns();
        PDPatternResources pattern = patterns.get(selectedPattern.getName()); 
        getContext().getGraphicsState().getStrokingColor().setPattern(pattern);
    }
}
