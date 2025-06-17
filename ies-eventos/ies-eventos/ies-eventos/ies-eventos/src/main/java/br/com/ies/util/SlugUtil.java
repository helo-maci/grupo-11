package br.com.ies.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

// Classe utilitária para geração de slugs a partir de textos
public class SlugUtil {
    // Expressão regular para remover caracteres não latinos
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    // Expressão regular para identificar espaços em branco
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    
    // Gera um slug a partir de uma string de entrada
    public static String gerarSlug(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        // Substitui espaços por hífens
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        // Normaliza caracteres acentuados
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        // Remove caracteres não latinos
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        // Retorna o slug em minúsculo
        return slug.toLowerCase(Locale.ENGLISH);
    }
} 