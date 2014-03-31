package com.example.vaadindemo;

import com.example.vaadindemo.model.MyValidator;
import com.example.vaadindemo.model.Person;
import com.vaadin.annotations.Title;
import com.vaadin.ui.*;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.IntegerRangeValidator;	
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Title("Week 3")
public class VaadinApp extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void init(VaadinRequest request) {
		VerticalLayout mainContainer = new VerticalLayout();

		// Table
		// ========================================================================
		final BeanItemContainer<Person> beanContainer = new BeanItemContainer<Person>(
				Person.class);

		beanContainer.addBean(new Person("Kaziu", "Khazix@wp.pl", 1960));
		beanContainer.addBean(new Person("Roman", "Romo@o2.pl", 1980));
		beanContainer.addBean(new Person("Woot", "Wootowsky@op.pl", 1991));

		final Table tabela = new Table();
		tabela.setContainerDataSource(beanContainer);
		tabela.setSelectable(true);
		tabela.setImmediate(true);

		// ========================================================================

		// form
		// ========================================================================
		Person ktos = new Person();
		BeanItem<Person> woot = new BeanItem<Person>(ktos);

		final FieldGroup fieldGroup = new FieldGroup();
		fieldGroup.setBuffered(false);
		fieldGroup.setItemDataSource(woot);

//		FormLayout form = new FormLayout();
//		form.addComponent(fieldGroup.buildAndBind("Imie", "imie"));
//		form.addComponent(fieldGroup.buildAndBind("Nazwisko", "nazwisko"));
//		form.addComponent(fieldGroup.buildAndBind("Rok Urodzenia",
//				"rokUrodzenia"));
//		form.setImmediate(true);

		// ========================================================================

		// funkcje
		// ========================================================================

		HorizontalLayout buttons = new HorizontalLayout();

		final Button btnDodaj = new Button("Dodaj");
		final Button btnUsun = new Button("Usuń");
		final Button btnEdytuj = new Button("Edytuj");

		buttons.addComponent(btnDodaj);
		buttons.addComponent(btnEdytuj);
		buttons.addComponent(btnUsun);

		// ========================================================================
		
		// Walidatory =============================================================
		
		FormLayout formLayout = new FormLayout();
		formLayout.setImmediate(true);
		
		Field<?> imieField = fieldGroup.buildAndBind("Imie","imie");
		imieField.setRequired(true);
		imieField.addValidator(new MyValidator());
		imieField.addValidator(new StringLengthValidator("Zła długośc", 3, 12, false));
		
		Field<?> emailField = fieldGroup.buildAndBind("E-mail","email");
		emailField.setRequired(true);
		emailField.addValidator(new EmailValidator("To nie jest E-mail"));
		
		Field<?> rokField = fieldGroup.buildAndBind("Rok Urodzenia","rokUrodzenia");
		rokField.addValidator(new IntegerRangeValidator("Nierealna data urodzenia", 1800, 2014));
		
		
		formLayout.addComponent(imieField);
		formLayout.addComponent(emailField);
		formLayout.addComponent(rokField);
		
		// ========================================================================
		
		mainContainer.addComponent(tabela);
		mainContainer.addComponent(formLayout);
		mainContainer.addComponent(buttons);

		tabela.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (tabela.getValue() != null) {

					Person temp = (Person) tabela.getValue();
					BeanItem<Person> person = new BeanItem<Person>(temp);
					fieldGroup.setItemDataSource(person);
				}
			}
		});

		btnDodaj.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Person toot = ((BeanItem<Person>) fieldGroup.getItemDataSource()).getBean();
				beanContainer.addBean(new Person(toot.getImie(), toot.getEmail(), toot.getRokUrodzenia()));
			}
		});
		btnUsun.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				beanContainer.removeItem(tabela.getValue());

			}
		});
		btnEdytuj.addClickListener(new ClickListener() {

			/**
	 * 
	 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					fieldGroup.commit();
					tabela.refreshRowCache();
				} catch (CommitException e) {
					e.printStackTrace();
				}

			}
		});

		setContent(mainContainer);
	}

}