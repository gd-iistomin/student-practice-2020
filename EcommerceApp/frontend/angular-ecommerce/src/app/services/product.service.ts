import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../common/product';
import { map } from 'rxjs/operators';
import { ProductCategory } from '../common/product-category';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  
  private baseUrl = 'http://localhost:8000/api/products';
  private categoryUrl = 'http://localhost:8000/api/product-category';

  private pageMaxSize: number = 1000;

  constructor(private httpClient: HttpClient) { }

  getProduct(theProductId: number): Observable<Product> {
    const productUrl = `${this.baseUrl}/${theProductId}`

    return this.httpClient.get<Product>(productUrl);
  }

  createProduct(theProduct: Product): Observable<any>{
    const productCreationUrl = 'http://localhost:8000/api/create-new-item';

    return this.httpClient.post<Product>(productCreationUrl, theProduct);
  }

  getProductListPaginate(thePage: number,
                        thePageSize: number,
                        theCategoryId: number): Observable<GetResponseProducts>{
    const listProductsByCategoryPaginationUrl = `${this.baseUrl}/search/findByCategoryId?id=${theCategoryId}&page=${thePage}&size=${thePageSize}`
    
    return this.httpClient.get<GetResponseProducts>(listProductsByCategoryPaginationUrl);
  }

  searchProductsPaginate(thePage: number,
                        thePageSize: number,
                        searchKeyword: string): Observable<GetResponseProducts> {
    const searchUrl = `${this.baseUrl}/search/findByNameContaining?name=${searchKeyword}&page=${thePage}&size=${thePageSize}`;

    return this.httpClient.get<GetResponseProducts>(searchUrl);
  }

  
  getProductList(categoryId: number): Observable<Product[]>{
    // size set to maxsize(1000) to get all products of category
    // (if not set explicitly, size defaults to 20, say we have 90 products, then 
    // without specifying size this method will get only 20 items(from first page))
    const listProductsByCategoryUrl = `${this.baseUrl}/search/findByCategoryId?id=${categoryId}&size=${this.pageMaxSize}`
    
    return this.getProducts(listProductsByCategoryUrl);
  }

  getEnabledProducts(categoryId: number): Observable<Product[]>{
    // size set to maxsize(1000) to get all enabled products
    const listActiveProductsByCategoryUrl = `${this.baseUrl}/search/findByCategoryIdAndActiveIsTrue?id=${categoryId}
                                                                                    &size=${this.pageMaxSize}`;
    
    return this.getProducts(listActiveProductsByCategoryUrl);
  }

  getProducts(Url: string): Observable<Product[]>{
    return this.httpClient.get<GetResponseProducts>(Url).pipe(map(response => response._embedded.products));
  }

  getProductCategories(): Observable<ProductCategory[]>{
    return this.httpClient.get<GetResponseProductCategory>(this.categoryUrl).pipe(map(response => response._embedded.productCategory));
  }

  
}

interface GetResponseProducts{
  _embedded: {
    products: Product[]
  },
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number;

  }
}

interface GetResponseProductCategory{
  _embedded: {
    productCategory: ProductCategory[]
  }
}

