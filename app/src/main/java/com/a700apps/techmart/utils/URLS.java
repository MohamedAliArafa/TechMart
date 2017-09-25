package com.a700apps.techmart.utils;

/**
 * Created by halima.reda on 12/6/2016.
 */
public class URLS {

    // enums
    public static final String PREF_NAME = "shared";
    public static final String PREF_Tocken_SIGNIN = "string";

    public static final String TAG_SELLER_REGISTER_STEP_ONE = "Seller register step one";
    public static final String TAG_SELLER_REGISTER_STEP_TWO = "Seller register step two";

    public static final String TAG_IMAGES_FRAGMENT = "images";
    public static final String TAG_VIDEOS_FRAGMENT = "videos";
    public static final String TAG_VIDEO_Details_FRAGMENT = "video Details";
    public static final String EXTRA_IMAGES = "images";
    public static final String TAG_SPEED = "speed";
    public static final String TAG_PRICE = "price";
    public static final String TAG_SHOW = "show";
    public static final String TAG_QUALITY = "qauality";
    public static final String TAG_PACK = "pack";


    public static final String TAG_FRAGMENT_OWNER_INFORMATION = "owner_information";
    public static final String TAG_FRAGMENT_DIRECTOR_INFORMATION = "director_information";
    public static final String TAG_FRAGMENT_SHOP_INFORMATION = "shop_information";
    public static final String TAG_FRAGMENT_PRODUCT_INFORMATION = "product_information";
    public static final String TAG_IS_LANGUAGE = "is_language";
    public static final String EXTRA_FAVORIRE_PAGE = "favorite";
    public static final String EXTRA_SHOPPING_PAGE = "shopping";
    public static final String EXTRA_SEARCH_PAGE = "search";
    public static final String EXTRA_MAIN_PAGE = "main";

    public static final String TAG_ITEMS = "items";
    public static final String TAG_TYPES = "types";
    public static final String TAG_CARATS = "carats";
    public static final String TAG_CLASSIFICATIONS = "classifications";
    public static final String TAG_CATEGORY = "category";
    public static final String TAG_SIZE = "size";
    public static final String TAG_AGE_GROUP = "age_group";
    public static final String TAG_FINISHING = "finishing";
    public static final String TAG_VIEW_SELLER = "viewSellerCompanyProfile";
    public static final String TAG_ALL_NEWS = "allNews";
    public static final String TAG_NEWS_DETAILS = "newsDetails";
    public static final String TAG_PRODUCT_DETAILS = "productDetails";
    public static final String TAG_COMPANY_ITEM = "SellerIDFromGetALLCompanyItem";
    public static final String TAG_SELLER_PRODUCTS_FROM_PROFILE = "SellerProductsFromProfile";



    public static final int TAG_SELLER_TYPE = 2;
    public static final int TAG_USER_TYPE = 1;

    public static final int TAG_ARABIC = 1;
    public static final int TAG_ENGLISH = 2;

    // main falgs
    public static final int FLAG_PORMOTIONAL_PRODUCTS = 1;
    public static final int FLAG_SEASONAL_PRODUCTS = 2;
    public static final int FLAG_NEW_PRODUCTS = 3;


    public static final int TAG_PROFILE = 1;
    public static final int TAG_MAIN_PAGE = 2;
    public static final int TAG_SEARCH_PAGE = 5;
    public static final int TAG_SHOPPING = 6;
    public static final int TAG_FAVORITE = 7;
    public static final int TAG_CONTACT_US = 8;
    public static final int TAG_COMPLAINT = 9;
    public static final int TAG_LANGUAGE = 10;
    public static final int TAG_ABOUT = 11;
    public static final int TAG_LOG_OUT = 12;
    public static final int TAG_NEWS = 3;
    public static final int TAG_FAQ = 4;
    public static final int TAG_PRODUCTS = 13;

    //Dialog
    public static final String DIALOG_LOG_OUT = "logout";
    public static final String DIALOG_CHANGE_LANGUAGE = "language";

    public static final String DIALOG_ADD_PHONE = "add_phone";
    public static final String DIALOG_FORGOT_PASSWORD = "forgot_password";
    public static final String DIALOG_RESET_PASSWORD = "reset_password";
    public static final String DIALOG_ADD_COMMENT = "add_comment";
    public static final String DIALOG_CONFIRM_PURCHASING = "confirm_purchasing";


    public static final String EXTRA_PRODUCT_ID = "product_id";
    public static final String EXTRA_PARCELABLE = "parcelable";
    public static final String EXTRA_TITLE = "title";


    public static final String URL_BASE_IMAGE = "http://108.179.204.213:8090/";
    //    public static final String URL_BASE_LOCAL = "http://192.168.20.111:8090/api/";
    public static final String URL_BASE = "http://108.179.204.213:8070/";

    // another local url
    //public static final String URL_BASE = "http://192.168.20.111:8070/";


    //  public static final String URL_BASE = " http://108.179.204.213:8097/";


//    public static final String URL_BASE_LOCAL = "http://192.168.20.111:8090/";


    public static final String URL_GET_ALL_Countries = "api/SettingApi/SellectLists";
    public static final String URL_SUGGESTION_AND_PROBLEM = "api/SettingApi/SuggestionandProblem";
    public static final String URL_GET_About_US = "api/SettingApi/GetAboutUS";
    public static final String URL_GET_ALL_FAQ = "api/SettingApi/GetALLFQA";
    public static final String URL_GET_FAQ_BY_ID = "api/SettingApi/GetFQABYID";
    public static final String URL_GET_ALL_VIDEOS = "api/SettingApi/GetALLVideos";
    public static final String URL_GET_ALL_IMAGES = "api/SettingApi/GetAllImages";
    public static final String URL_GET_MEDIA_BY_ID = "api/SettingApi/GetMediaByID";
    public static final String URL_GET_ALL_NEWS = "api/SettingApi/GetALLNews";
    public static final String URL_GET_NEWS_BY_ID = "api/SettingApi/GetNewsBYID";
    public static final String URL_GET_CONTACT_US = "api/SettingApi/GetContactUS";
    public static final String URL_GET_ALL_ADDS = "api/SettingApi/GetALLAdds";
    public static final String URL_GET_ADDS_BY_ID = "api/SettingApi/GetAddsBYID";
    public static final String URL_DELETE_COMPANY_ITEM_BY_ID = "api/ProductsApi/DeleteItem";
    public static final String URL_CLIENT_REGISTER = "api/Account/Register";
    public static final String URL_CLIENT_LOGIN = "api/Account/Login";
    public static final String URL_GET_USER_BY_ID = "api/Account/SelectUserByID";
    public static final String URL_GET_SELLER_DATA = "api/Account/SelectSellerByID";
    public static final String URL_GET_SELLER_DATA_FOR_VIEW = "api/Account/SelectSellerByIDNew";

    public static final String URL_GET_COMPANY_PRODUCT_DETAILS = "api/ProductsApi/SelectCompanyItemsDetails";
    public static final String URL_GET_PRODUCT_DETAILS_FOR_UPDATE = "api/ProductsApi/GetProductDateForUpdate";
    public static final String URL_GET_PRODUCT_DETAILS = "api/ProductsApi/GetProductsBYID";
    public static final String URL_UPDATE_SELLER_Profile = "api/Account/UpdateProfile";
    public static final String URL_RESET_PASSWORD = "api/Account/ResetPassword";
    public static final String URL_CHANGE_PASSWORD = "api/Account/changePassWord";
    public static final String URL_SAVE_FILE = "api/ProductsApi/SaveFile";
    public static final String URL_SAVE_FILE_LIST = "api/ProductsApi/SaveFileLst";
    public static final String URL_MOST_SELLER = "api/ProductsApi/MostSeller";
    public static final String URL_NEW_OFFERS = "api/ProductsApi/NewOffres";

    public static final String URL_SEARCH = "api/ProductsApi/Search";
    public static final String URL_SEARCH_COMPANY = "api/Account/searchcompany";
    public static final String FILTERITEM = "api/ProductsApi/filterItem";

    public static final String URL_FILTER_ITEM = "api/ProductsApi/filterItem";
    public static final String URL_ADD_COMMENT = "api/ProductsApi/Addcomment";
    public static final String URL_PRODUCT_DETAILS_BY_ID = "api/ProductsApi/SelectCompanyItemsDetails";
    public static final String URL_MOST_SHOW = "api/ProductsApi/MostShow";
    public static final String URL_MAKE_ORDER = "api/OrderApi/MakeOrder";
    public static final String URL_USER_UPDATE_BILL_STATE = "api/OrderApi/UpdateSate";
    public static final String URL_USER_UPDATE_BILL_RATE = "api/OrderApi/UpdateRate";
    public static final String URL_ADD_CART_OR_WISH = "api/CartApi/AddCartOrWishNew";
    public static final String URL_ADD_WISH = "api/CartApi/AddCartOrWishNew";
    public static final String URL_GET_PRODUCTS = "api/ProductsApi/GetALLCatograyItems";
    public static final String URL_ALL_LISTS = "api/SettingApi/SellectLists";
    public static final String URL_SELLER_ACCEPTED_PRODUCTS = "api/ProductsApi/SelectCompanyItemsActive";
    public static final String URL_SELLER_PENDING_PRODUCTS = "api/ProductsApi/SelectCompanyItemsNotActive";
    public static final String URL_ADD_TO_WISH_LIST = "api/CartApi/AddCartOrWish";
    public static final String URL_ADD_PRODUCT = "api/ProductsApi/IsertUpdateProduct";
    public static final String URL_COMPARE = "api/ProductsApi/CompareItems";
    public static final String URL_GET_WISH_LIST_BY_ID = "api/CartApi/Userwish";
    public static final String URL_GET_CART_LIST_BY_ID = "api/CartApi/UserCart";
    public static final String URL_DELETE_CART_LIST_BY_ID = "api/CartApi/DeleteCartOrWish";
    public static final String URL_DELETE_WISH_LIST_BY_ID_FROM_LIST = "api/CartApi/DeleteCartOrWishFromList";
    public static final String URL_USER_BILLS_BY_ID = "api/OrderApi/Checkout";
    public static final String URL_USER_NEW_BILLS_BY_ID = "api/OrderApi/NewCheckout";
    public static final String URL_USER_BILLS_LIST = "api/OrderApi/UserOrderList";
    public static final String URL_SELLER_ORDERS_LIST = "api/OrderApi/orderList";
    public static final String URL_RATE_PRODUCT = "api/ProductsApi/AddRate";
    public static final String URL_SELLER_ORDER_DETAILS = "api/OrderApi/CompanyOrdersDetailes";
    public static final String URL_SELLER_CHANGE_ORDER_STATE = "api/OrderApi/UpdateSate";
    public static final String URL_SELLER_BILLS_BY_ID = "api/OrderApi/BillList";
    public static final String URL_SELLER_SALLES_BY_ID = "api/OrderApi/sellesList";
    public static final String URL_SELLER_SEARCH_ITEM_SALES = "api/OrderApi/searchSelles";
    public static final String URL_SELLER_BILLS_DETAIL_BY_ID = "api/OrderApi/CompanyBillDetailes";
    public static final String URL_COMPANY_BILL_RATE_BY_ID = "api/OrderApi/CompanyBillRate";
    public static final String URL_Get_ALL_Company_Items = "api/ProductsApi/GetALLCompanyItems";
    public static final String URL_USER_BILLS_DETAIL_BY_ID = "api/OrderApi/UserOrdersDetailes";
    public static final String URL_CART_WISH_NOTIFY_BY_USERID = "api/Account/GetCartWishNotfy";
    public static final String URL_UPDATE_TOKEN = "/api/Account/updatetoken";
    public static final String URL_GET_NOTFICATION_LIST = "api/Notfcation/GetUserNotfy";
    public static final String URL_Update_NOTFICATION_LIST = "api/Notfcation/updateNotfy";


    public static final int TAG_RESULT_Upload_gallery_IMG = 8;
    public static final int TAG_RESULT_Take_IMG = 19;
    public static final int FACEBOOK_LOGIN = 9;
    public static final String TWITTER_KEY = "3NYhatz1ME6D56rMQr549PAJR";
    public static final String TWITTER_SECRET = "hnqsrHsuxjQBrq1bX3NxmYkwV0GLQuMOqtG6kczJPN2yf8FPHv";
    public static final String TAG_PENDING_PRODUCTS = "pending products";
    public static final String TAG_ACCEPTED_PRODUCTS = "accepted products";
    public static final String TAG_SALES_PRODUCTS = "sales products";
    public static final String TAG_BILLS_PRODUCTS = "bills products";
    public static final String TAG_SEARCH_BILLS = "TAG_SEARCH_BILLS";
    public static final String TAG_COME_FROM_CART = "TAG_COME_FROM_CART";
    public static final String TAG_COME_FROM_WISH = "TAG_COME_FROM_WISH";
    public static final String TAG_COME_FROM_COMPARE = "TAG_COME_FROM_COMPARE";
    public static final String TAG_COME_FROM_GetAllCompanyItemFragment = "TAG_COME_FROM_GetAllCompanyItemFragment";
    public static final String TAG_COME_FROM_NOTIFI_LIST = "TAG_COME_FROM_NOTIFI_LIST";
    public static final String TAG_COME_FROM_ProductsFragment = "TAG_COME_FROM_ProductsFragment";
    public static final String TAG_COME_FROM_Related_Product = "TAG_COME_FROM_Related_Product";


    public static final String TAG_SEARCH_PRODUCTS = "search products";
    public static final String YOUTUBE_API_KEY = "AIzaSyBwq0lsNrfDcuxBazcL7sW3ZYiPHr5aAqk";
    public static final String DIALOG_CHANGE_ADDRESS = "change_address";


}
