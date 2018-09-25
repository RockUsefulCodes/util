package br.com.rockstom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Define a group of rules to validade passwords
 * Arquivo: PasswordRules.java <br/>
 * @since 23 de mai de 2018
 * @author Wesley Luiz
 * @version 1.0.0
 */
public final class PasswordRules {
	private final CharSequence password;
	private final Collection<PasswordValidator> validators;
	private final List<String> messages;

	private PasswordRules(CharSequence pwd) {
		password = pwd;

		this.messages = new ArrayList<>();
		this.messages.add("Must not be empty or null");
		
		this.validators = new ArrayList<>();
		this.validators.add(pwd2 -> pwd2 != null && !pwd2.toString().trim().isEmpty());
	}
	
	/**
	 * Get an instance of builder
	 * @author Wesley Luiz
	 * @param password
	 * @return
	 */
	public static PasswordRules getInstance(CharSequence password) {
		return new PasswordRules(password);
	}
	
	/**
	 * Check if password has at least X digits
	 * @author Wesley Luiz
	 * @param characters
	 * @return
	 */
	public PasswordRules atLeastXNumber(int characters) {
		this.validators.add(pwd -> {
			Matcher mt = Pattern.compile("[0-9]").matcher(pwd);
			
			int qnt = 0;
			
			while (mt.find()) qnt++;
			
			return qnt >= characters;
		});
		
		this.messages.add(String.format("Must be at least %d digits", characters));
		return this;
	}
	
	/**
	 * Check if password has at least X uppercased character
	 * @author Wesley Luiz
	 * @param characters
	 * @return
	 */
	public PasswordRules atLeastXUppercasedCharacter(int characters) {
		this.validators.add(pwd -> Pattern.compile("^(.*?[A-Z]){"+characters+",}.*$").matcher(pwd).matches());
		this.messages.add(String.format("Must be at least %d uppercased characters", characters));
		return this;
	}
	
	/**
	 * Check if password has more than X charaters
	 * @author Wesley Luiz
	 * @param characters
	 * @return
	 */
	public PasswordRules moreThan(int characters) {
		this.validators.add(pwd -> pwd.length() > characters);
		this.messages.add(String.format("Must be bigger than %d", characters));
		return this;
	}
	
	/**
	 * Apply all validators and return a result:
	 * <code>true</code> if it's all ok 
	 * or <code>false</code> if at least one is failed
	 * @author Wesley Luiz
	 * @return
	 */
	public boolean validate() {
		return this.validators.stream().allMatch(v -> v.valid(password));
	}
	
	/**
	 * Apply all validators and throw an exception just in case something fail
	 * @author Wesley Luiz
	 * @throws InvalidPasswordException
	 */
	public void validateOrThrowException() throws Exception {
		int i = 0;
		
		for (PasswordValidator validator : this.validators) {
			if (!validator.valid(password)) {
				throw new Exception(this.messages.get(i));
			}
			
			i++;
		}
	}
	
	private interface PasswordValidator {
		boolean valid(CharSequence password);
	}
}
