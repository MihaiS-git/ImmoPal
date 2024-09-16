import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PropertyDto } from '../../../dto/propertyDto.model';

@Component({
  selector: 'app-sale',
  templateUrl: './sale.component.html',
  styleUrl: './sale.component.css'
})
export class SaleComponent implements OnInit {
  allProperties: PropertyDto[] = [];
  filteredProperties: PropertyDto[] = [];
  paginatedProperties: PropertyDto[] = [];
  categories: string[] = ['APARTMENT', 'STUDIO', 'VILLA', 'CHALET', 'COMMERCIAL', 'OFFICE', 'INDUSTRIAL'];
  selectedCategory: string = 'All';
  sortOrder: 'asc' | 'desc' = 'asc';

  thePageNumber: number = 1;
  thePageSize: number = 8;
  theTotalElements: number = 0;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.allProperties = data['properties'] || [];
      this.filteredProperties = [...this.allProperties];
      this.applySortingAndPagination();
    });
  }

  public applySortingAndPagination() {
    this.sortProperties();
    this.paginateProperties();
  }

  private sortProperties() {
    if (this.sortOrder === 'asc') {
      this.filteredProperties.sort((a, b) => a.price - b.price);
    } else if (this.sortOrder === 'desc') {
      this.filteredProperties.sort((a, b) => b.price - a.price);
    }
  }

  private paginateProperties() {
    const startIndex = (this.thePageNumber - 1) * this.thePageSize;
    const endIndex = startIndex + this.thePageSize;
    this.paginatedProperties = this.filteredProperties.slice(startIndex, endIndex);
    this.theTotalElements = this.filteredProperties.length;
  }

  updatePageSize(pageSize: string) {
    this.thePageSize = +pageSize;
    this.thePageNumber = 1;
    this.applySortingAndPagination();
  }

  sortByPriceAscending() {
    this.sortOrder = 'asc';
    this.applySortingAndPagination();
  }

  sortByPriceDescending() {
    this.sortOrder = 'desc';
    this.applySortingAndPagination();
  }

  filterByCategory(category: string) {
    this.selectedCategory = category;
    this.thePageNumber = 1;
    this.filteredProperties = this.selectedCategory === 'All'
      ? [...this.allProperties]
      : this.allProperties.filter(p => p.propertyCategory === this.selectedCategory);
    this.applySortingAndPagination();
  }

  clearFilters() {
    this.selectedCategory = 'All';
    this.thePageNumber = 1;
    this.filteredProperties = [...this.allProperties];
    this.applySortingAndPagination();
  }
}
