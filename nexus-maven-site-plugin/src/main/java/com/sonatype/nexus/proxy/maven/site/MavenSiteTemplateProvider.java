/**
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2012 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package com.sonatype.nexus.proxy.maven.site;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.sonatype.nexus.proxy.registry.RepositoryTypeDescriptor;
import org.sonatype.nexus.proxy.registry.RepositoryTypeRegistry;
import org.sonatype.nexus.proxy.repository.WebSiteRepository;
import org.sonatype.nexus.templates.TemplateProvider;
import org.sonatype.nexus.templates.TemplateSet;
import org.sonatype.nexus.templates.repository.AbstractRepositoryTemplateProvider;

@Component( role = TemplateProvider.class, hint = MavenSiteTemplateProvider.PROVIDER_ID )
public class MavenSiteTemplateProvider
    extends AbstractRepositoryTemplateProvider
    implements Initializable
{
    public static final String PROVIDER_ID = "site-repository";

    private static final String MAVEN_SITE_ID = "maven-site";

    @Requirement
    private RepositoryTypeRegistry repositoryTypeRegistry;

    public TemplateSet getTemplates()
    {
        TemplateSet templates = new TemplateSet( null );

        try
        {
            templates.add( new MavenSiteTemplate( this, MAVEN_SITE_ID, "Maven Site (hosted)" ) );
        }
        catch ( Exception e )
        {
            // will not happen
        }

        return templates;
    }

    public void initialize()
        throws InitializationException
    {
        repositoryTypeRegistry.registerRepositoryTypeDescriptors( new RepositoryTypeDescriptor(
            WebSiteRepository.class, "maven-site", "sites" ) );
    }
}