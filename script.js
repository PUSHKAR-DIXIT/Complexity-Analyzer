function analyzeCode() {
    let code = document.getElementById("code").value;

    let loopCount = (code.match(/for|while/g) || []).length;
    let ifCount = (code.match(/if/g) || []).length;
    let recursion = code.includes("function") && code.includes("(") && code.includes(")");

    // TIME COMPLEXITY
    let timeComplexity = "O(1)";

    if (recursion) {
        timeComplexity = "O(2ⁿ) (Recursive)";
    } 
    else if (loopCount === 1) {
        timeComplexity = "O(n)";
    } 
    else if (loopCount === 2) {
        timeComplexity = "O(n²)";
    } 
    else if (loopCount >= 3) {
        timeComplexity = "O(n³)";
    }
    if (code.includes("mergeSort") && code.includes("merge(")) {
    timeComplexity = "O(n log n)";
}


    // SPACE COMPLEXITY
    let arrayCount = (code.match(/\[\]/g) || []).length;
    let spaceComplexity = arrayCount > 0 ? "O(n)" : "O(1)";

    document.getElementById("time").innerText =
        "Time Complexity: " + timeComplexity;

    document.getElementById("space").innerText =
        "Space Complexity: " + spaceComplexity;
}
