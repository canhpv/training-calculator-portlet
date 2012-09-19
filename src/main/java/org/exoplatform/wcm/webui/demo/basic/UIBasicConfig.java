/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.wcm.webui.demo.basic;

import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;

import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIForm;
import org.exoplatform.webui.form.UIFormStringInput;

/**
 * Created by The eXo Platform SAS
 * Author : Canh Pham Van
 *          canhpv@exoplatform.com
 * Sep 18, 2012  
 */
@ComponentConfig(
 lifecycle = UIFormLifecycle.class,
 template = "system:/groovy/webui/form/UIForm.gtmpl",
     events = {
        @EventConfig(listeners = UIBasicConfig.SaveActionListener.class),
        @EventConfig(listeners = UIBasicConfig.CancelActionListener.class)
     }
 )
 public class UIBasicConfig extends UIForm {

    public static final String TEXT_STRING_INPUT = "UIBasicPortletTextStringInput";

    public UIBasicConfig() {
         PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
         PortletPreferences preferences = portletRequestContext.getRequest().getPreferences();
         String text = preferences.getValue(UIBasicPortlet.TEXT_PREFERENCE, null);
         addChild(new UIFormStringInput(TEXT_STRING_INPUT, text));
     }

    public static class SaveActionListener extends EventListener<UIBasicConfig> {
        public void execute(Event<UIBasicConfig> event) throws Exception {
             UIBasicConfig basicConfig = event.getSource();
             UIFormStringInput textStringInput = basicConfig.getUIStringInput(TEXT_STRING_INPUT);
             PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
             PortletPreferences preferences = portletRequestContext.getRequest().getPreferences();
             preferences.setValue(UIBasicPortlet.TEXT_PREFERENCE, textStringInput.getValue());
             preferences.store();
             PortletRequestContext context = (PortletRequestContext) event.getRequestContext();
             context.setApplicationMode(PortletMode.VIEW);
         }
     }

    public static class CancelActionListener extends EventListener<UIBasicConfig> {
        public void execute(Event<UIBasicConfig> event) throws Exception {
             PortletRequestContext context = (PortletRequestContext) event.getRequestContext();
             context.setApplicationMode(PortletMode.VIEW);
         }
     }
 }