import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Product } from 'src/app/common/product';
import { ProductCategory } from 'src/app/common/product-category';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-item-creation',
  templateUrl: './item-creation.component.html',
  styleUrls: ['./item-creation.component.css']
})
export class ItemCreationComponent implements OnInit {

  newItemFormGroup: FormGroup;

  productCategories: ProductCategory[];

  constructor(private authenticationService: AuthenticationService,
              private formBuilder: FormBuilder,
              private productService: ProductService,
              private router: Router) { }

  ngOnInit(): void {

    // creating new item form
    this.newItemFormGroup = this.formBuilder.group({
      // all fields in the form are represented by FormControl, we specify initial value: '' and validators for the given field. 
      sku: new FormControl(''),
      name: new FormControl(''),
      description: new FormControl(''),
      unitPrice: new FormControl(''),
      imageUrl: new FormControl(''),
      category: new FormControl('')
      
      })

    //getting product categories
    this.getProductCategories();  
  }

  getProductCategories(){
    this.productService.getProductCategories().subscribe(
      data => { this.productCategories = data; }
    );
  }
  

  createItem() {
    let product: Product = new Product();
    product.sku = this.newItemFormGroup.controls['sku'].value;
    product.name = this.newItemFormGroup.controls['name'].value;
    product.description = this.newItemFormGroup.controls['description'].value;
    product.unitPrice = this.newItemFormGroup.controls['unitPrice'].value;
    product.imageUrl = this.newItemFormGroup.controls['imageUrl'].value;
    product.active = true;
    product.unitsInStock = 100;
    product.category = new ProductCategory();

    const productCategoryId: number = +this.newItemFormGroup.controls['category'].value;
    console.log("category =" + productCategoryId)
    for (let tempcategory of this.productCategories) {
      if(tempcategory.id == productCategoryId){
        product.category.id = tempcategory.id;
        product.category.categoryName = tempcategory.categoryName;
      }
    }
    
    console.log(product)
    this.productService.createProduct(product).subscribe({ 
      next: response => {
            alert('Product has been created.');
            this.router.navigateByUrl("/admin-dashboard");
        },

      error: err => {
          alert(`There was an error: ${err.message}`)
        }
    });
    
  }

  authenticated() { return this.authenticationService.authenticated.value; }

  getAuthority() {
     return this.authenticationService.authority.value;
  }

  

}
