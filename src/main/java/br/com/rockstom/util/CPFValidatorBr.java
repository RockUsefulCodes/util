package br.com.rockstom.util;

import java.util.Arrays;

public final class CPFValidatorBr {

	private static final int LENGHT_CPF = 11;
	
	private static final String[] SEQ_INVALID_NUMBERS = new String[] {
			"00000000000","11111111111","22222222222","33333333333",
			"44444444444","55555555555","66666666666","77777777777",
			"88888888888","99999999999"
		};

	/**
	 * Construtor Default.
	 */
	private CPFValidatorBr() {
		super();
	}

	public static boolean isValid(final String cpf) {
		
		boolean resultado = false;

		int posicao;

		final String strCpf = StringUtil.removeSpecialCharacter(cpf);

		if (strCpf != null && strCpf.length() == CPFValidatorBr.LENGHT_CPF) {
			
			if (Arrays.asList(SEQ_INVALID_NUMBERS).stream()
					.anyMatch(v -> v.equals(strCpf))) {
				return false;
			}

			String cleanCpf = strCpf.trim();

			posicao = 0;

			for (int i = 0; i < 9; i++) {
				posicao += (10 - i) * (cleanCpf.charAt(i) - 48);
			}

			posicao = 11 - posicao % 11;

			if (posicao > 9) {
				posicao = 0;
			}

			if (posicao == cleanCpf.charAt(9) - 48) {

				posicao = 0;

				for (int i = 0; i < 10; i++) {
					posicao += (11 - i) * (cleanCpf.charAt(i) - 48);
				}

				posicao = 11 - posicao % 11;

				if (posicao > 9) {
					posicao = 0;
				}

				if (posicao == cleanCpf.charAt(10) - 48) {
					resultado = true;
				}
			}
		}

		return resultado;
	}

}
