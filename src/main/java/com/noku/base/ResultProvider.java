package com.noku.base;

public interface ResultProvider {
    /**
     * Checks if result is a boolean or a {@link ResultRow}.
     * @return true if the result of the query is a boolean.
     */
    public boolean isBool();

    /**
     * Checks to see if result is valid or not.
     * @return true if the result was successful.
     */
    public boolean isSuccessful();

    /**
     * Gets the number of rows from query.
     * @return number of rows returned from query.
     */
    public int getRowCount();

    /**
     * Gathers all {@link ResultRow} in result and returns them.
     * @return an array of {@link ResultRow} from query.
     */
    public ResultRow[] getRows();

    /**
     * Gathers a row at specified index
     * @param index of row
     * @return the {@link ResultRow} at the specified index.
     */
    public ResultRow getRow(int index);
}
