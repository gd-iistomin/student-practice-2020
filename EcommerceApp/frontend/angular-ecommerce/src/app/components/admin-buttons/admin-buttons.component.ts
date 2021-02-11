import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-buttons',
  templateUrl: './admin-buttons.component.html',
  styleUrls: ['./admin-buttons.component.css']
})
export class AdminButtonsComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  showAdminDashboard() {
    this.router.navigateByUrl(`/admin-dashboard`)
  }

}
