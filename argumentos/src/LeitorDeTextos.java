import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LeitorDeTextos {

    private static final String VOWELS = "aeiouAEIOU";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java LeitorDeTextos <texto>");
            System.exit(1);
        }

        String text = String.join(" ", args);
        List<String> words = extractWords(text);

        printResults(text, words);
    }

    private static List<String> extractWords(String text) {
        return Arrays.stream(text.trim().split("\\s+"))
                .map(LeitorDeTextos::removeNonLetters)
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toList());
    }

    private static String removeNonLetters(String word) {
        return word.replaceAll("[^a-zA-ZÀ-ÿ]", "");
    }

    private static String removeAccents(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    private static long countVowels(String text) {
        String normalized = removeAccents(text);
        return normalized.chars()
                .filter(character -> VOWELS.indexOf(character) >= 0)
                .count();
    }

    private static long countWordsWithOddLength(List<String> words) {
        return words.stream()
                .filter(word -> word.length() % 2 != 0)
                .count();
    }

    private static long countWordsWithEvenLength(List<String> words) {
        return words.stream()
                .filter(word -> word.length() % 2 == 0)
                .count();
    }

    private static double toPercentage(long part, int total) {
        if (total == 0) return 0.0;
        return (part * 100.0) / total;
    }

    private static void printSeparator() {
        System.out.println("─".repeat(40));
    }

    private static void printResults(String text, List<String> words) {
        int totalWords = words.size();
        long totalVowels = countVowels(text);
        long oddCount = countWordsWithOddLength(words);
        long evenCount = countWordsWithEvenLength(words);

        printSeparator();
        System.out.println("        ANÁLISE DE TEXTO");
        printSeparator();
        System.out.printf("Texto: \"%s\"%n", text);
        printSeparator();
        System.out.printf("Quantidade de palavras : %d%n", totalWords);
        System.out.printf("Quantidade de vogais   : %d%n", totalVowels);
        printSeparator();
        System.out.println("  ESTATÍSTICA DE LETRAS POR PALAVRA");
        printSeparator();
        System.out.printf("Palavras com letras ímpares : %d  (%.1f%%)%n",
                oddCount, toPercentage(oddCount, totalWords));
        System.out.printf("Palavras com letras pares   : %d  (%.1f%%)%n",
                evenCount, toPercentage(evenCount, totalWords));
        printSeparator();
    }
}