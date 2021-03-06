/*****************************************************************************
 * 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 ****************************************************************************/

package org.apache.padaf.xmpbox.schema;

import java.util.ArrayList;
import java.util.List;

import org.apache.padaf.xmpbox.XMPMetadata;
import org.apache.padaf.xmpbox.type.AbstractField;
import org.apache.padaf.xmpbox.type.Attribute;
import org.apache.padaf.xmpbox.type.BadFieldValueException;
import org.apache.padaf.xmpbox.type.ComplexProperty;
import org.apache.padaf.xmpbox.type.JobType;

public class XMPBasicJobTicketSchema extends XMPSchema {

    public static final String PREFERED_JOB_TICKET_PREFIX = "xmpBJ";

    public static final String JOB_TICKET_URI = "http://ns.adobe.com/xap/1.0/bj/"; 

    @PropertyType(propertyType = "bag Job")
    public static final String JOB_REF = "JobRef";

    protected ComplexProperty bagJobs;


    public XMPBasicJobTicketSchema(XMPMetadata metadata) {
        this(metadata, PREFERED_JOB_TICKET_PREFIX);
    }

    public XMPBasicJobTicketSchema(XMPMetadata metadata, String ownPrefix) {
        super(metadata, ownPrefix, JOB_TICKET_URI);
        content.setAttribute(new Attribute(NS_NAMESPACE, "xmlns",
                JobType.PREFERED_PREFIX, JobType.ELEMENT_NS));

    }

    public void addJob(String id , String name, String url) {
        if (bagJobs == null) {
            bagJobs = new ComplexProperty(metadata, localPrefix, JOB_REF,
                    ComplexProperty.UNORDERED_ARRAY);
            addProperty(bagJobs);
        }
        JobType job = new JobType(metadata, "rdf", "li");
        job.setId("stJob", id);
        job.setName("stJob", name);
        job.setUrl("stJob", url);
        bagJobs.getContainer().addProperty(job);
    }

    public List<JobType> getJobs() throws BadFieldValueException {
        List<AbstractField> tmp = getArrayList(localPrefixSep + JOB_REF);
        if (tmp != null) {
            List<JobType> layers = new ArrayList<JobType>();
            for (AbstractField abstractField : tmp) {
                if (abstractField instanceof JobType) {
                    layers.add((JobType) abstractField);
                } else {
                    throw new BadFieldValueException("Job expected and "
                            + abstractField.getClass().getName() + " found.");
                }
            }
            return layers;
        }
        return null;

    }


}
