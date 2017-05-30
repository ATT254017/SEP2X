package Client;

import java.util.List;

import Model.Category;
import Model.MethodStatus;

public interface GetCategoriesResponseHandler
{
	void handle(MethodStatus status, List<Category> categories);
}
