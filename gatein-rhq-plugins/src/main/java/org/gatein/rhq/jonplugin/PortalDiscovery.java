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

package org.gatein.rhq.jonplugin;

import org.gatein.rhq.Portal;
import org.gatein.rhq.jmx.GateInJMXResourceDiscovery;
import org.rhq.core.pluginapi.inventory.DiscoveredResourceDetails;
import org.rhq.core.pluginapi.inventory.ResourceDiscoveryContext;
import org.rhq.plugins.jmx.JMXComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PortalDiscovery extends GateInJMXResourceDiscovery
{
   private static final Logger log = LoggerFactory.getLogger(PortalDiscovery.class);

   protected DiscoveredResourceDetails createResourceDetail(ResourceDiscoveryContext<JMXComponent<?>> context, String portalContainerName, String name)
   {
      Portal.PortalKey key = Portal.PortalKey.create(portalContainerName, name);

      String portalDescription = key.toString();

      log.info("Discovered new " + portalDescription);

      return new DiscoveredResourceDetails(context.getResourceType(), Portal.PortalKey.compose(key),
         key.getPortalName(), "version", "Monitoring of GateIn resources for " + portalDescription, null, null);
   }

   public String getAttributeName()
   {
      return "PortalList";
   }
}