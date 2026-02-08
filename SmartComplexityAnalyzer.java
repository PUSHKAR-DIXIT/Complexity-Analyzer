import java.util.regex.*;

public class SmartComplexityAnalyzer {

    public static void main(String[] args) {

        // 👇 USER CODE (Website se input aayega)
        String userCode = """
            int[] arr = new int[n];

            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    System.out.println(arr[i] + arr[j]);
                }
            }
        """;

        analyze(userCode);
    }

    public static void analyze(String code) {

        int loopCount = countMatches(code, "\\bfor\\b|\\bwhile\\b");
        int nestedDepth = detectNestedLoops(code);
        boolean logLoop = detectLogLoop(code);
        boolean recursion = detectRecursion(code);
        boolean arraySpace = detectArray(code);
        boolean dpTable = detectDP(code);
        boolean backtracking = detectBacktracking(code);
        boolean graph = detectGraph(code);

        // ---------------- TIME COMPLEXITY ----------------
        String time = "O(1)";

        if (graph) {
            time = "O(V + E)";
        }
        else if (backtracking) {
            time = "O(2^n) / O(n!)";
        }
        else if (dpTable) {
            time = "O(n^2) / DP Based";
        }
        else if (recursion && loopCount > 0) {
            time = "O(n log n)";
        }
        else if (recursion) {
            time = "O(n)";
        }
        else if (nestedDepth > 1) {
            time = "O(n^" + nestedDepth + ")";
        }
        else if (loopCount == 1) {
            time = logLoop ? "O(log n)" : "O(n)";
        }

        // ---------------- SPACE COMPLEXITY ----------------
        String space = "O(1)";

        if (graph) {
            space = "O(V + E)";
        }
        else if (dpTable) {
            space = "O(n^2)";
        }
        else if (arraySpace && recursion) {
            space = "O(n) + recursion stack";
        }
        else if (recursion) {
            space = "O(n)";
        }
        else if (arraySpace) {
            space = "O(n)";
        }

        // ---------------- OUTPUT ----------------
        System.out.println("📌 TIME COMPLEXITY  : " + time);
        System.out.println("📌 SPACE COMPLEXITY : " + space);
    }

    // ================= HELPER METHODS =================

    static int countMatches(String code, String regex) {
        Matcher m = Pattern.compile(regex).matcher(code);
        int count = 0;
        while (m.find()) count++;
        return count;
    }

    static int detectNestedLoops(String code) {
        int depth = 0;
        int maxDepth = 0;

        for (String line : code.split("\\n")) {
            if (line.contains("for") || line.contains("while"))
                depth++;
            if (line.contains("}"))
                depth = Math.max(0, depth - 1);
            maxDepth = Math.max(maxDepth, depth);
        }
        return maxDepth;
    }

    static boolean detectLogLoop(String code) {
        return code.contains("/= 2") || code.contains(">>");
    }

    static boolean detectRecursion(String code) {
        Pattern p = Pattern.compile("(\\w+)\\s*\\(");
        Matcher m = p.matcher(code);
        if (m.find()) {
            String name = m.group(1);
            return code.indexOf(name + "(") != code.lastIndexOf(name + "(");
        }
        return false;
    }

    static boolean detectArray(String code) {
        return code.contains("new int") || code.contains("new String")
                || code.contains("ArrayList") || code.contains("HashMap");
    }

    static boolean detectDP(String code) {
        return code.contains("dp[") || code.contains("dp =");
    }

    static boolean detectBacktracking(String code) {
        return code.contains("solve(") && detectRecursion(code);
    }

    static boolean detectGraph(String code) {
        return code.contains("adj") || code.contains("graph")
                || code.contains("BFS") || code.contains("DFS");
    }
}



