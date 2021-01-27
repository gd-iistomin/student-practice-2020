import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Product } from 'src/app/common/product';
import { ProductCategory } from 'src/app/common/product-category';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-item-disable',
  templateUrl: './item-disable.component.html',
  styleUrls: ['./item-disable.component.css']
})
export class ItemDisableComponent implements OnInit {

  disableItemFormGroup: FormGroup;

  productCategories: ProductCategory[];

  enabledProducts: Product[];

  constructor(private authenticationService: AuthenticationService,
              private formBuilder: FormBuilder,
              private productService: ProductService,
              private router: Router,
              private httpClient: HttpClient) { }

  ngOnInit(): void {

    // creating disable item form
    this.disableItemFormGroup = this.formBuilder.group({
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

    const categoryId = this.disableItemFormGroup.get(formControlName).value;

    this.productService.getEnabledProducts(categoryId).subscribe( data => {
        this.enabledProducts = data;
    })

  }

  disableItem(){
    const targetItemId: number = +this.disableItemFormGroup.controls['product'].value;
    const disableItemUrl = `http://localhost:8000/api/disable-product/${targetItemId}`;
    this.httpClient.put<any>(disableItemUrl, null).subscribe( data => { });
    alert('Product has been disabled!');
    this.router.navigateByUrl("/admin-dashboard");
  }

  authenticated() { return this.authenticationService.authenticated.value; }

  getAuthority() {
     return this.authenticationService.authority.value;
  }

}
