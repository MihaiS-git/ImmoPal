import { Component, Input, OnInit } from '@angular/core';
import { AgentDto } from '../../../../dto/agentDto.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-agency-agent-list',
  templateUrl: './agency-agent-list.component.html',
  styleUrl: './agency-agent-list.component.css'
})
export class AgencyAgentListComponent implements OnInit{
  @Input() agents: AgentDto[] = [];
  agencyId: number;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.parent.paramMap.subscribe(params => {
      this.agencyId = +params.get('agencyId');
    });
  }
}
