package com.vaadin.addon.chameleon;

import java.util.Iterator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class SidebarMenu extends CssLayout {

    public SidebarMenu() {
        addStyleName("sidebar-menu");
    }

    public SidebarMenu addButton(NativeButton b) {
        addComponent(b);
        b.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                updateButtonStyles();
                event.getButton().addStyleName("selected");
            }
        });
        return this;
    }

    private void updateButtonStyles() {
        for (Iterator<Component> iterator = getComponentIterator(); iterator
                .hasNext();) {
            Component c = iterator.next();
            c.removeStyleName("selected");
        }
    }

    public void setSelected(NativeButton b) {
        updateButtonStyles();
        b.addStyleName("selected");
    }
}
