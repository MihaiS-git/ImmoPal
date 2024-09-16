import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyService } from '../../../../service/property.service';
import { AgentDto } from '../../../../dto/agentDto.model';
import { AgencyService } from '../../../../service/agency.service';
import { PropertyDto } from '../../../../dto/propertyDto.model';

@Component({
  selector: 'app-agent-properties',
  templateUrl: './agent-properties.component.html',
  styleUrl: './agent-properties.component.css'
})
export class AgentPropertiesComponent implements OnInit {
  properties: PropertyDto[];
  agentId: number;
  agent: AgentDto;

  thePageNumber: number = 1;
  thePageSize: number = 8;
  theTotalElements: number = 0;

  constructor(private route: ActivatedRoute,
    private propertyService: PropertyService,
    private router: Router,
    private agencyService: AgencyService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.agentId = +params.get('agentId');
      this.listProperties();
      const agents: AgentDto[] = this.agencyService.getAgents();
      this.agent = agents.find(agent => agent.id === this.agentId);
      if (!this.agent) {
        console.error(`Agent with ID ${this.agentId} not found.`);
      } else {
        console.log("Agent:", this.agent);
      }
    });
  }

  listProperties() {
    this.propertyService.getPropertiesByAgentIdPaginate(
      this.thePageNumber - 1,
      this.thePageSize,
      this.agentId
    ).subscribe(this.processResult());
  }

  processResult() {
    return (data: any) => {
      this.properties = data.content;
      this.thePageNumber = data.pageable.pageNumber + 1;
      this.thePageSize = data.pageable.pageSize;
      this.theTotalElements = data.totalElements;
    };
  }

  updatePageSize(pageSize: string) {
    this.thePageSize = +pageSize;
    this.thePageNumber = 1;
    this.listProperties();
  }

  sortByPriceAscending() {
    this.properties.sort((a, b) => a.price - b.price);
  }

  sortByPriceDescending() {
    this.properties.sort((a, b) => b.price - a.price);
  }

  navigateToProperty(propertyId: number) {
    this.router.navigate(['/properties', propertyId]);
  }

}
