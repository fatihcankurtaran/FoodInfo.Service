package Remote;

import com.example.count.foodinformation.VoteDTO;

import Model.AdminDTO;
import Model.BarcodeDTO;
import Model.CategoryClass;
import Model.CategoryLanguage;
import Model.CategoryNameDTO;
import Model.CommentDTO;
import Model.ContentDTO;
import Model.CreateUserClass;
import Model.ErrorClass;
import Model.GroupProductDTO;
import Model.GroupProductDTO2;
import Model.LanguageAndProductDTO;
import Model.LanguagesClass;
import Model.LoginClass;
import Model.ModeratorDTO;
import Model.Parameter;
import Model.PostResponse;
import Model.ProductDTO;
import Model.ProductDTO2;
import Model.ProductDTO3;
import Model.SearchByNameDTO;
import Model.UpdateCategoryNameDTO;
import Model.UserDTO;
import Model.UserDTO2;
import Model.Values;
import Model.productNameClass;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service
{
  //******LoginController***********

  @POST("/api/Login/CheckUserOnLogin/")
  Call<LoginClass> Login(@Body LoginClass value);

  //******ProductController***********

  @POST("/api/Product/CreateProduct/")
  Call<ProductDTO> CreateProduct(@Body ProductDTO value);
  @POST("/api/Product/GetProductNameByBarcodeId/")
  Call<ProductDTO> GetProductNameByBarcodeId(@Body BarcodeDTO value);
  @POST("/api/Product/SearchProductByName/")
  Call<SearchByNameDTO> SearchProductByName(@Query("productname") String name);


  //******UserController***********
  @POST("/api/User/GetAllUsersOrderByName/")
  Call<UserDTO2> GetAllUsersOrderByName();
  @POST("/api/User/DeleteUserByUsername/")
  Call<AdminDTO> DeleteUserByUsername(@Body UserDTO value);
  @POST("/api/User/CreateUser/")
  Call<CreateUserClass> CreateUser(@Body CreateUserClass value);
  @POST("/api/User/GetUserDetailByUsername/")
  Call<UserDTO> GetUserDetailByUsername(@Body UserDTO value);
  @POST("/api/User/SetModeratorByUsername/")
  Call<ModeratorDTO> SetModeratorByUsername(@Body UserDTO value);
  @POST("/api/User/SetNormalUserByUsername/")
  Call<AdminDTO> SetNormalUserByUsername(@Body UserDTO userDTO);
  @POST("/api/User/SetAdminByUsername/")
  Call<AdminDTO> SetAdminByUsername(@Body UserDTO userDTO);
  @POST("/api/User/GetFirstUser/")
  Call<UserDTO> GetFirstUser();

  //******GroupController***********
  @POST("/api/Product/GroupProducts/")
  Call<GroupProductDTO> GroupProducts(@Body GroupProductDTO groupProdutsDTO);
  @POST("/api/Product/AllProducts/")
  Call<ProductDTO3> AllProducts();
  @POST("/api/Product/DeleteProduct/")
  Call<Void> DeleteProduct(@Query("BarcodeId") String BarcodeId);
  //******ErrorController***********

  @POST("/api/Language/GetLanguageList/")
  Call<LanguagesClass> Languages();
  @POST("/api/Language/GetLanguageListOfProductByBarcodeId/")
  Call<LanguagesClass> GetLanguageListOfProductByBarcodeId(@Body BarcodeDTO barcodeDTO);

    //******ErrorController***********

  @POST("/api/Error/GetErrorList/")
  Call<ErrorClass> Errors();
  //******CommentController***********
  @POST("/api/Comment/AddComment/")
  Call<CommentDTO> AddComment(@Body CommentDTO value);

    //******ContentController***********
  @POST("/api/Content/GetProductContentByLanguageCode/")
  Call<ContentDTO> GetProductContentByLanguageCode(@Body LanguageAndProductDTO contentDTO);
  @POST("/api/Content/CreateContentOfProduct/")
  Call<ContentDTO> CreateContentOfProduct(@Body ContentDTO contentDTO);

  @POST("/api/Content/UpdateContent/")
  Call<ContentDTO> UpdateContent(@Body ContentDTO contentDTO);


   //******VoteController***********
   @POST("/api/Vote/AddVote/")
   Call<VoteDTO> AddVote(@Body VoteDTO value);

   //******CategoryController***********

  @POST("/api/Category/GetProductCategoriesByLanguageCode/")
  Call<CategoryClass> Categories(@Body CategoryLanguage categoryLanguage);
  @POST("/api/Category/AddCategory/")
  Call<CategoryNameDTO> AddCategory(@Body CategoryNameDTO categoryLanguage);
  @POST("/api/Category/DeleteCategoryByLanguageCode/")
  Call<CategoryNameDTO> DeleteCategoryByLanguageCode(@Body CategoryNameDTO categoryLanguage);
  @POST("/api/Category/UpdateCategoryName/")
  Call<UpdateCategoryNameDTO> UpdateCategoryName(@Body UpdateCategoryNameDTO categoryLanguage);
}
