package inProgressKattis.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MoreMultiplication {
    private static Scanner _scanner;
    private static String cellBorder = "|";
    private static List<String> rowsCells;
    private static List<int[][]> rowsNumbers;

    private static String number1 = "";
    private static String number2 = "";

    private static List<String> listOfNumber1;
    private static List<String> listOfNumber2;

    public static void main(String[] args) {
        _scanner = new Scanner(System.in);

        listOfNumber1 = new ArrayList<>();
        listOfNumber2 = new ArrayList<>();

        // number1 = "345";
        // number2 = "56";

        while (_scanner.hasNext()) {
            number1 = _scanner.nextInt() + "";
            number2 = _scanner.nextInt() + "";
            if (Integer.parseInt(number1) == 0 || Integer.parseInt(number2) == 0)
                continue;
            _scanner.nextLine();
            listOfNumber1.add(number1);
            listOfNumber2.add(number2);
        }

        for (int i = 0; i < listOfNumber1.size(); i++) {
            number1 = listOfNumber1.get(i);
            number2 = listOfNumber2.get(i);
            processCreationOfTable();
        }

    }

    private static void processCreationOfTable() {
        List<String[]> rowsData = new ArrayList<>();
        String calcResult;
        rowsCells = new ArrayList<>();
        rowsNumbers = new ArrayList<>();
        for (int innerNr = 0; innerNr < number2.length(); innerNr++) {
            rowsData.add(new String[number1.length()]);
            rowsNumbers.add(new int[2][number1.length()]);
            for (int outNr = number1.length() - 1; outNr >= 0; outNr--) {
                calcResult = String.valueOf(
                        Integer.parseInt(number1.charAt(outNr) + "") * Integer.parseInt(number2.charAt(innerNr) + ""));

                char char1 = calcResult.charAt(0);
                char char2 = calcResult.length() == 2 ? calcResult.charAt(1) : '0';

                if (calcResult.length() == 1) {
                    char1 = '0';
                    char2 = calcResult.charAt(0);
                }

                rowsData.get(innerNr)[outNr] = createCellBody(char1,
                        char2);

                // Add the numbers to the numbers list
                rowsNumbers.get(innerNr)[0][outNr] = Integer.parseInt(char1 + ""); // Tens
                rowsNumbers.get(innerNr)[1][outNr] = Integer.parseInt(char2 + ""); // Ones
                // System.out.println(rowsData.get(innerNr)[outNr]);
            }
            rowsCells.add(
                    placeNumberVertically(number2.charAt(innerNr) + "", joinCells(rowsData.get(innerNr))));
        }

        // Go through the "ones" on the right side
        for (int row = rowsCells.size() - 1; row >= 0; row--) {
            Sum sum = calcSum(row, rowsNumbers.get(row)[1].length - 1, true);
            // Add the reminder to the next set of numbers
            if (row > 0 && sum.getReminder() > 0) { // Add it to the last "one" number of the previous rows
                rowsNumbers.get(row - 1)[1][rowsNumbers.get(row - 1)[1].length - 1] += sum.getReminder();
            } else if (sum.getReminder() > 0) { // Add it to the last "tens" number of the current row
                rowsNumbers.get(0)[0][rowsNumbers.get(0)[0].length - 1] += sum.getReminder();
            }
        }

        // Go through the tens and sum them;
        // for(int row = 0; row < rowsCells.size(); row++){
        for (int numberIndex = rowsNumbers.get(0)[0].length - 1; numberIndex >= 0; numberIndex--) {
            Sum sum = calcSum(0, numberIndex, false);
            if (sum.getReminder() > 0) {
                rowsNumbers.get(0)[0][numberIndex - 1] += sum.getReminder();
            }
        }
        // }

        String headerCell = addHeader(number1, rowsCells.remove(0));
        rowsCells.add(0, headerCell);

        // Add border to last row
        String newLastRow = appendBorderToLastRow(rowsCells.getLast());
        rowsCells.removeLast();
        rowsCells.add(newLastRow);

        // Add side slashes
        // for (int i = 1; i < rowsCells.size(); i++) {
        // String newRow = addSlashToLeftOfFirstRow(rowsCells.remove(i));
        // rowsCells.add(i, newRow);
        // }

        System.out.println(addOuterBorder(joinRows()));
    }

    private static void updateRowByAddingSideSlash(int rowIndex) {
        String newRow = addSlashToLeftOfFirstRow(rowsCells.remove(rowIndex));
        rowsCells.add(rowIndex, newRow);
    }

    // private static String removeLastSlashForOne(String ){

    // }

    private static String addOuterBorder(String innerTable) {
        StringBuilder table = new StringBuilder();
        table.append(createOuterBorderLine(number1.length()) + "\n");
        String[] tableRows = innerTable.split("\n");

        for (String row : tableRows) {
            table.append("|" + row + "|\n");
        }

        table.append(createOuterBorderLine(number1.length()));
        return table.toString();
    }

    private static String createOuterBorderLine(int length) {
        StringBuilder line = new StringBuilder("+-");

        for (int i = 0; i < length; i++) {
            line.append("----");
        }

        line.append("--+");
        return line.toString();
    }

    private static String joinRows() {
        StringBuilder joinedRows = new StringBuilder();
        for (int i = 0; i < rowsCells.size(); i++) {
            joinedRows.append(rowsCells.get(i));
            if ((i + 1) < rowsCells.size()) {
                joinedRows.append(createHorBorder(number1.length()) + "\n");
            }
        }

        return joinedRows.toString();
    }

    private static String addSlashToLeftOfFirstRow(String row) {
        String[] rowData = row.split("\n");
        rowData[0] = rowData[0].replaceFirst(" ", "/");
        StringBuilder newRow = new StringBuilder();

        for (int i = 0; i < rowData.length; i++) {
            newRow.append(rowData[i] + "\n");
        }

        return newRow.toString();
    }

    private static String appendBorderToLastRow(String lastRow) {
        String[] lastRowData = lastRow.split("\n");
        StringBuilder newLastRow = new StringBuilder();

        for (int i = 0; i < lastRowData.length; i++) {
            newLastRow.append(lastRowData[i] + "\n");
            if (i + 2 == lastRowData.length) {
                // Add the border
                newLastRow.append(createHorBorder(number1.length()) + "\n");
            }
        }

        return newLastRow.toString();
    }

    private static Sum calcSum(int rowIndex, int indexOfNr, boolean startFromOnes) {
        int reminder = 0;
        int sum = 0;

        int shiftRowNrsIndex = startFromOnes ? 1 : 0;
        int indexOffset = indexOfNr;
        int row = rowIndex;
        for (; row < rowsNumbers.size(); row++) {
            for (int rowNrs = shiftRowNrsIndex; rowNrs < 2; rowNrs++) {
                sum += rowsNumbers.get(row)[rowNrs][indexOffset];
                if (rowNrs == 0)
                    indexOffset--;

                if (indexOffset < 0) { // Reached the end
                    if (sum >= 10) {
                        reminder = sum / 10;
                        sum -= reminder * 10;
                    }

                    appendSumToRow(row, sum, -1);

                    return new Sum(sum, reminder);
                }
            }
            if (row == rowIndex)
                shiftRowNrsIndex = 0; // Reset the offset as it only should apply in the first run
        }

        if (sum >= 10) {
            reminder = sum / 10;
            sum -= reminder * 10;
        }

        appendSumToRow(row - 1, sum, indexOffset);

        return new Sum(sum, reminder);
    }

    private static void appendSumToRow(int rowIndex, int sum, int cellIndex) {

        // Get row data
        String rowData = rowsCells.get(rowIndex);
        StringBuilder updatedRowData = new StringBuilder();
        if (cellIndex < 0) { // Append to the right side of the last row

            String[] rowRows = rowData.split("\n");
            rowRows[0] = " " + rowRows[0] + "\n";
            rowRows[1] = " " + rowRows[1] + "\n";

            // Make sure to avoid writing zeros for the last number
            if (sum == 0 && rowIndex == 0) {
                rowRows[2] = " " + rowRows[2] + "\n";
            } else {
                rowRows[2] = sum + rowRows[2] + "\n";
            }

            for (int i = 0; i < rowRows.length; i++) {
                updatedRowData.append(rowRows[i]);
            }

            // Add slash to the next row if exists
            if (rowIndex < rowsCells.size() - 1 && sum != 0) {
                updateRowByAddingSideSlash(rowIndex + 1);
            }

        } else {
            String[] rowRows = rowData.split("\n");
            rowRows[0] += "\n";
            rowRows[1] += "\n";
            rowRows[2] += "\n";

            StringBuilder lastLine = new StringBuilder();

            if (cellIndex == number1.length() - 1) {
                for (int i = 0; i < number1.length() - 1; i++) {
                    lastLine.append("/   ");
                }
                lastLine.append("/ " + sum + "    ");
            } else {
                String[] lastLineData = rowRows[3].split("/");

                for (int i = 1; i < lastLineData.length; i++) {
                    if ((i - 1) != cellIndex) {
                        lastLineData[i] = "/" + lastLineData[i];
                    } else {
                        lastLineData[i] = "/ " + sum + " ";
                    }

                    lastLine.append(lastLineData[i]);
                }
            }

            for (int i = 0; i < 3; i++) {
                updatedRowData.append(rowRows[i]);
            }

            updatedRowData.append(
                    (number1.length() == 1 && number2.length() == 1) ? lastLine.toString().replaceFirst("/", " ")
                            : lastLine);

        }

        rowsCells.remove(rowIndex);
        rowsCells.add(rowIndex, updatedRowData.toString());
    }

    private static String createCellBody(char upperNumber, char lowerNumber) {
        StringBuilder cell = new StringBuilder();

        cell.append(upperNumber + " /\n");
        cell.append(" / \n");
        cell.append("/ " + lowerNumber + "\n");

        return cell.toString();
    }

    private static String placeNumberVertically(String number, String table) {
        String[] rows = table.split("\n");

        rows[0] += " \n";
        rows[1] += number + "\n";
        rows[2] += " \n";

        return rows[0] + rows[1] + rows[2];
    }

    private static String joinCells(String[] cells) {
        StringBuilder joinedCellsRow1 = new StringBuilder(cellBorder);
        StringBuilder joinedCellsRow2 = new StringBuilder(cellBorder);
        StringBuilder joinedCellsRow3 = new StringBuilder(cellBorder);

        String[] cellRows;
        for (int i = 0; i < cells.length; i++) {
            cellRows = cells[i].split("\n");
            joinedCellsRow1.append(cellRows[0] + cellBorder);
            joinedCellsRow2.append(cellRows[1] + cellBorder);
            joinedCellsRow3.append(cellRows[2] + cellBorder);
        }

        return joinedCellsRow1.toString() + "\n"
                + joinedCellsRow2.toString() + "\n"
                + joinedCellsRow3.toString() + "\n";
    }

    private static String addHeader(String numbers, String row) {
        StringBuilder rowWithHeader = new StringBuilder();

        // Add the header
        rowWithHeader.append("  ");
        for (int i = 0; i < numbers.length(); i++) {
            rowWithHeader.append(" " + numbers.charAt(i) + "  ");
        }

        rowWithHeader.append(" ");

        rowWithHeader.append("\n" + createHorBorder(numbers.length()) + "\n");

        rowWithHeader.append(row);

        return rowWithHeader.toString();
    }

    private static String createHorBorder(int length) {
        StringBuilder border = new StringBuilder(" +");
        for (int i = 0; i < length; i++) {
            border.append("---+");
        }

        border.append(" ");
        return border.toString();
    }

    // private static String createCell()

    public static class Sum {
        private int _reminder;
        private int _sum;

        public Sum() {
            _reminder = 0;
            _sum = 0;
        }

        public Sum(int sum, int reminder) {
            _sum = sum;
            _reminder = reminder;
        }

        public int getReminder() {
            return _reminder;
        }

        public int getSum() {
            return _sum;
        }
    }
}
