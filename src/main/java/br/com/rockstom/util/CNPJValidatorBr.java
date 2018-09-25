package br.com.rockstom.util;

public final class CNPJValidatorBr {

	private static final int LENGTH_MASCARA_CNPJ = 19;

	private static final int LENGTH_CNPJ = 14;

	/**
	 * Construtor.
	 */
	private CNPJValidatorBr() {
		super();
	}

	
	public static boolean isValid(final Number cnpj) {
		boolean result = false;
		if (cnpj != null) {
			result = CNPJValidatorBr.isValid(cnpj.toString());
		}
		return result;
	}
	

	public static boolean isValid(final long cnpj) {
		return CNPJValidatorBr.isValid(Long.valueOf(cnpj));
	}

	
	public static boolean isValid(final String cnpj) {
		String strCnpj = StringUtil.removeSpecialCharacter(cnpj);
		boolean result;
		int position;
		if (strCnpj == null || !(strCnpj.length() >= CNPJValidatorBr.LENGTH_CNPJ)) {
			return false;
		}

		result = false;
		strCnpj = strCnpj.trim();
		final int tamanho = strCnpj.length();
		if (tamanho > CNPJValidatorBr.LENGTH_CNPJ && tamanho <= CNPJValidatorBr.LENGTH_MASCARA_CNPJ) {
			strCnpj = StringUtil.removeSpecialCharacter(strCnpj);
		}
		if (strCnpj.length() > CNPJValidatorBr.LENGTH_CNPJ) {
			return false;
		}

		position = 0;
		int i = 0;
		int j = 5;

		for (; i < 12; i++) {
			position += j-- * (strCnpj.charAt(i) - 48);
			if (j < 2) {
				j = 9;
			}
		}

		position = 11 - position % 11;
		if (position > 9) {
			position = 0;
		}

		if (position == strCnpj.charAt(12) - 48) {
			position = 0;
			i = 0;
			j = 6;

			for (; i < 13; i++) {
				position += j-- * (strCnpj.charAt(i) - 48);
				if (j < 2) {
					j = 9;
				}
			}

			position = 11 - position % 11;

			if (position > 9) {
				position = 0;
			}

			if (position == strCnpj.charAt(13) - 48) {
				result = true;
			}
		}
		return result;
	}

}
