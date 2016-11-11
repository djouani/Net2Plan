package com.net2plan.io.excelIO;

/**
 * @author Jorge San Emeterio
 * @date 11-Nov-16
 */
public class Net2PlanExcelException extends RuntimeException
{
    public Net2PlanExcelException()
    {
        super();
    }

    public Net2PlanExcelException(final String message)
    {
        super(message);
    }

    public Net2PlanExcelException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public Net2PlanExcelException(final Throwable cause)
    {
        super(cause);
    }
}
