/*
 * JBoss, a division of Red Hat
 * Copyright 2011, Red Hat Middleware, LLC, and individual
 * contributors as indicated by the @authors tag. See the
 * copyright.txt in the distribution for a full listing of
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.gatein.rhq.plugin;

import org.gatein.rhq.Portal;
import org.gatein.rhq.ResourceKey;
import org.gatein.rhq.jmx.GateInJMXResourceComponent;
import org.gatein.rhq.jmx.JMXPortalStatisticService;
import org.gatein.rhq.spi.stats.PortalStatisticService;
import org.mc4j.ems.connection.bean.EmsBean;
import org.rhq.core.domain.configuration.Configuration;
import org.rhq.core.domain.measurement.MeasurementDataNumeric;
import org.rhq.core.domain.measurement.MeasurementScheduleRequest;
import org.rhq.core.pluginapi.operation.OperationResult;


public class PortalComponent extends GateInJMXResourceComponent<Portal, PortalStatisticService>
{
   @Override
   protected void initManagedResource(ResourceKey key, EmsBean statisticBean)
   {
      PortalStatisticService statisticService = new JMXPortalStatisticService(statisticBean, getCurrentResourceName(key));
      managedResource = new Portal(key, statisticService);
   }

   @Override
   protected String getCurrentResourceName(ResourceKey key)
   {
      Portal.PortalKey portalKey = key.getPortalKey();
      return portalKey.getPortalName();
   }

   @Override
   protected MeasurementDataNumeric getMeasurementOrNullFor(MeasurementScheduleRequest req, String name)
   {
      if (name.equals("throughput"))
      {
         return new MeasurementDataNumeric(req, managedResource.getThroughput());
      }
      else
      {
         return null;
      }
   }

   public OperationResult invokeOperation(String name, Configuration params) throws Exception
   {
      OperationResult res = new OperationResult();
      if ("refreshPortletList".equals(name))
      {
         System.out.println("refreshPortletList called res = " + res);
      }
      return res;

   }

   @Override
   public String getAttributeName()
   {
      return "PortalList";
   }
}