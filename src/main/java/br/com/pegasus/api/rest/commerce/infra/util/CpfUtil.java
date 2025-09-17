package br.com.pegasus.api.rest.commerce.infra.util;

public final class CpfUtil {

  /**
   * Verifica se o cpf é valido:
   *
   * <ul style="list-style-type: disc;">
   *   <li>Se o parametro tem tamanho 11</li>
   *   <li>Se os 11 dígitos não são iguais (ex: 00000000000)</li>
   *   <li>Se o cpf é logicamente válido</li>
   *   <li>Não verifica parametro nulo</li>
   *   <li><span style="color:#808080">Não verifica parametro nulo</span></b></li>
   * </ul>
   *
   * @param cpf O cpf a ser verificado.
   * @return true para cpf válido.
   */
  public static boolean valid(String cpf) {
    String cpfOnlyDigits = cpf.replaceAll(ConstUtil.REGEX_N_DIG, ConstUtil.T_EMPTY);
    if (cpfOnlyDigits.length() == ConstUtil.N_11 && !cpfOnlyDigits.matches(ConstUtil.REGEX_ALL_SAME_DIGITS)) {
      return verifyDigits(cpfOnlyDigits, calcFirstDigit(cpfOnlyDigits), calcSecondDigit(cpfOnlyDigits) );
    }
    return false;
  }

  private static int calcFirstDigit(final String cpfOnlyDigits) {
    int digitSum = ConstUtil.N_0;
    for (int index = ConstUtil.N_0; index < ConstUtil.N_9; index++) {
      digitSum += (cpfOnlyDigits.charAt(index) - ConstUtil.C_0) * (ConstUtil.N_10 - index);
    }
    int digit = ConstUtil.N_11 - (digitSum % ConstUtil.N_11);
    return (digit > ConstUtil.N_9) ? ConstUtil.N_0 : digit;
  }

  private static int calcSecondDigit(final String cpfOnlyDigits) {
    int digitSum = ConstUtil.N_0;
    for (int i = ConstUtil.N_0; i < ConstUtil.N_10; i++) {
      digitSum += (cpfOnlyDigits.charAt(i) - ConstUtil.C_0) * (ConstUtil.N_11 - i);
    }
    int digit = ConstUtil.N_11 - (digitSum % ConstUtil.N_11);
    return (digit > ConstUtil.N_9) ? ConstUtil.N_0 : digit;
  }

  private static boolean verifyDigits(final String cpfOnlyDigits, final int firstDigit, final int secondDigit) {
  return (cpfOnlyDigits.charAt(ConstUtil.N_9) - ConstUtil.C_0 == firstDigit)
      && (cpfOnlyDigits.charAt(ConstUtil.N_10) - ConstUtil.C_0 == secondDigit);
  }

}
