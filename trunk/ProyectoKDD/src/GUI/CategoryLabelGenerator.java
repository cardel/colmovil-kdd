/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gema
 */
package GUI;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.labels.CategoryItemLabelGenerator;

public class CategoryLabelGenerator implements CategoryItemLabelGenerator
{
    public String generateRowLabel(CategoryDataset dataset, int row)
    {
        return "";
    }

    public String generateColumnLabel(CategoryDataset dataset, int column)
    {
        return "";
    }

    public String generateLabel(CategoryDataset dataset, int row, int column)
    {
        Number d = dataset.getValue(row, column);

        if(d.intValue() == 0)
        {
            return "";
        }
        else
        {
            return d.toString();
        }
    }

}
