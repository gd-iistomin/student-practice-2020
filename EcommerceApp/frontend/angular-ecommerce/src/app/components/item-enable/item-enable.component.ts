import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Product } from 'src/app/common/product';
import { ProductCategory } from 'src/app/common/product-category';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-item-enable',
  templateUrl: './item-enable.component.html',
  styleUrls: ['./item-enable.component.css']
})
export class ItemEnableComponent implements OnInit {

  enableItemFormGroup: FormGroup;

  productCategories: ProductCategory[];

  disabledProducts: Product[];

  constructor(private authenticationService: AuthenticationService,
              private formBuilder: FormBuilder,
              private productService: ProductService,
              private router: Router,
              private httpClient: HttpClient) { }

  ngOnInit(): void {

    // creating enable item form
    this.enableItemFormGroup = this.formBuilder.group({
      // all fields in the form are represented by FormControl, we specify initial value: '' and validators for the given field. 
      category: new FormControl(''),
      product: new FormControl('')
      })

    //getting product categories
    this.getProductCategories(); 
  }

  getProductCategories(){
    this.productService.getProductCategories().subscribe(
      data => { this.productCategories = data; }
    );
  }

  getProducts(formControlName: string){

    const categoryId = this.enableItemFormGroup.get(formControlName).value;

    this.productService.getDisabledProducts(categoryId).subscribe( data => {
        this.disabledProducts = data;
    })

  }

  enableItem(){
    const targetItemId: number = +this.enableItemFormGroup.controls['product'].value;
    const enableItemUrl = `http://localhost:8000/api/enable-product/${targetItemId}`;
    this.httpClient.put<any>(enableItemUrl, null).subscribe( data => { });
    alert('Product has been enabled!');
    this.router.navigateByUrl("/admin-dashboard");
  }

  authenticated() { return this.authenticationService.authenticated.value; }

  getAuthority() {
     return this.authenticationService.authority.value;
  }

}
