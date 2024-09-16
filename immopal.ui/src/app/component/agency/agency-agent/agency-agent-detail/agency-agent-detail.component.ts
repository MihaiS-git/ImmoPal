import { Component, Input } from '@angular/core';
import { AgentDto } from '../../../../dto/agentDto.model';

@Component({
  selector: 'app-agency-agent-detail',
  templateUrl: './agency-agent-detail.component.html',
  styleUrl: './agency-agent-detail.component.css'
})
export class AgencyAgentDetailComponent {
  @Input() agent: AgentDto;

}
