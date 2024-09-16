import { ActivatedRoute } from '@angular/router';
import { Component, OnInit, Output } from '@angular/core';
import { AgentDto } from '../../../dto/agentDto.model';
import { AgencyService } from '../../../service/agency.service';

@Component({
  selector: 'app-agency-agent',
  templateUrl: './agency-agent.component.html',
  styleUrls: ['./agency-agent.component.css']
})
export class AgencyAgentComponent implements OnInit {
  agents: AgentDto[] = [];
  agencyId!: number;

  constructor(private route: ActivatedRoute, private agencyService: AgencyService) { }

  ngOnInit(): void {
    this.route.parent?.paramMap.subscribe(param => {
      if (param) {
        this.agencyId = +param.get('agencyId')!;
        if (this.agencyId) {
          this.agencyService.getAgentsbyAgencyId(this.agencyId).subscribe(agents => {
            this.agents = agents;
          });
        }
      } else {
        console.log("The agencyId is not found in the activated route params");
      }
    });
  }
}
