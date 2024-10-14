package inProgressKattis.medium;

import java.util.ArrayList;
import java.util.List;

public class MoreMultiplication {
    private static String cellBorder = "|";

    public static void main(String[] args) {

        String number1 = "345";
        String number2 = "56";

        List<String[]> rowsData = new ArrayList<>();
        String calcResult;
        List<String> rowsCells = new ArrayList<>();
        for (int innerNr = 0; innerNr < number2.length(); innerNr++) {
            rowsData.add(new String[number1.length()]);
            for (int outNr = number1.length() - 1; outNr >= 0; outNr--) {
                calcResult = String.valueOf(
                        Integer.parseInt(number1.charAt(outNr) + "") * Integer.parseInt(number2.charAt(innerNr) + ""));
                rowsData.get(innerNr)[outNr] = createCellBody(calcResult.charAt(0), calcResult.charAt(1));
                // System.out.println(rowsData.get(innerNr)[outNr]);
            }
            rowsCells.add(joinCells(rowsData.get(innerNr)));
        }

        String headerCell = addHeader(number1, rowsCells.remove(0));
        rowsCells.add(0, headerCell);

        for (String s : rowsCells) {
            System.out.println(s);
        }

    }

    private static String createCellBody(char upperNumber, char lowerNumber) {
        StringBuilder cell = new StringBuilder();

        cell.append(upperNumber + " /\n");
        cell.append(" / \n");
        cell.append("/ " + lowerNumber + "\n");

        return cell.toString();
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
        rowWithHeader.append(" ");
        for (int i = 0; i < numbers.length(); i++) {
            rowWithHeader.append(" " + numbers.charAt(i) + "  ");
        }

        rowWithHeader.append("\n" + createHorBorder(numbers.length()) + "\n");

        rowWithHeader.append(row);

        return rowWithHeader.toString();
    }

    private static String createHorBorder(int length) {
        StringBuilder border = new StringBuilder("+");
        for (int i = 0; i < length; i++) {
            border.append("---+");
        }

        return border.toString();
    }

    // private static String createCell()
}
