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

package org.gatein.rhq.jmx;

import org.gatein.rhq.spi.stats.TimedStatisticService;
import org.mc4j.ems.connection.bean.EmsBean;
import org.mc4j.ems.connection.bean.operation.EmsOperation;

/**
 * @author <a href="mailto:chris.laprun@jboss.com">Chris Laprun</a>
 * @version $Revision$
 */
public class JMXTimedStatisticService implements TimedStatisticService
{
   private final EmsOperation getMinTime;
   private final EmsOperation getMaxTime;
   private final EmsOperation getAverageTime;
   private final String serviceName;

   public JMXTimedStatisticService(EmsBean statisticJMXBean, String serviceName)
   {
      if (statisticJMXBean == null) throw new IllegalArgumentException("statisticJMXBean cannot be null");
      if (serviceName == null || serviceName.trim().length() == 0) throw new IllegalArgumentException("serviceName cannot be null or empty");

      getMinTime = statisticJMXBean.getOperation("getMinTime", (Class[]) null);
      getMaxTime = statisticJMXBean.getOperation("getMaxTime", (Class[]) null);
      getAverageTime = statisticJMXBean.getOperation("getAverageTime", (Class[]) null);

      this.serviceName = serviceName;
   }

   @Override
   public double getMinExecutionTime()
   {
      return (Double)getMinTime.invoke(serviceName);
   }

   @Override
   public double getMaxExecutionTime()
   {
      return (Double)getMaxTime.invoke(serviceName);
   }

   @Override
   public String getServiceName()
   {
      return serviceName;
   }

   @Override
   public double getAverageExecutionTime()
   {
      return (Double)getAverageTime.invoke(serviceName);
   }
}
