import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { AgenciesListComponent } from './component/agency/agencies-list/agencies-list.component';
import { AgencyResolverService } from './resolver/agency-resolver.service';
import { AgenciesDetailComponent } from './component/agency/agencies-detail/agencies-detail.component';
import { AgencyComponent } from './component/agency/agency.component';
import { AgencyPropertyComponent } from './component/agency/agency-property/agency-property.component';
import { AgencyAgentComponent } from './component/agency/agency-agent/agency-agent.component';
import { SaleComponent } from './component/property/sale/sale.component';
import { RentComponent } from './component/property/rent/rent.component';
import { PropertyComponent } from './component/property/property.component';
import { PropertySaleResolverService } from './resolver/property-sale-resolver.service';
import { PropertyRentResolverService } from './resolver/property-rent-resolver.service';
import { PropertyPageComponent } from './component/property/property-page/property-page.component';
import { PropertyResolverService } from './resolver/property-resolver.service';
import { AgentPropertiesComponent } from './component/agency/agency-agent/agent-properties/agent-properties.component';
import { RegisterComponent } from './component/register/register.component';
import { AuthComponent } from './component/auth/auth.component';
import { AccountComponent } from './component/account/account.component';
import { AdminComponent } from './component/admin/admin.component';
import { RecoverComponent } from './component/recover/recover.component';
import { ResetPasswordComponent } from './component/recover/reset-password/reset-password.component';
import { AuctionComponent } from './component/auction/auction.component';
import { RoomsListComponent } from './component/auction/rooms-list/rooms-list.component';
import { RoomComponent } from './component/auction/room/room.component';
import { AuctionResolverService } from './resolver/auction-resolver.service';
import { NewRoomComponent } from './component/auction/new-room/new-room.component';
import { FavouritesComponent } from './component/auction/favourites/favourites.component';
import { ClosedComponent } from './component/auction/closed/closed.component';
import { ChatRoomComponent } from './component/chat/chat-room/chat-room.component';
import { ChatListComponent } from './component/chat/chat-list/chat-list.component';
import { ChatResolverService } from './resolver/chat-resolver.service';
import { AppointmentsComponent } from './component/appointments/appointments.component';


const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'auth', component: AuthComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'recover', component: RecoverComponent, children: [
      { path: 'reset-password', component: ResetPasswordComponent },
      { path: 'reset-password/:token', component: ResetPasswordComponent }
    ]
  },
  {
    path: 'auctions', component: AuctionComponent, resolve: { auctionRooms: AuctionResolverService }, children: [
      { path: 'rooms-list', component: RoomsListComponent },
      { path: 'rooms-list/:roomId', component: RoomComponent },
      { path: 'new-room', component: NewRoomComponent },
      { path: 'favourites', component: FavouritesComponent },
      { path: 'closed', component: ClosedComponent },
    ]
  },
  { path: 'account', component: AccountComponent },
  { path: 'admin', component: AdminComponent },
  {
    path: 'agencies',
    component: AgencyComponent,
    resolve: { agencies: AgencyResolverService },
    children: [
      { path: '', component: AgenciesListComponent, pathMatch: 'full' },
      {
        path: ':agencyId',
        component: AgenciesDetailComponent,
        children: [
          { path: 'agents', component: AgencyAgentComponent },
          { path: 'agents/:agentId', component: AgentPropertiesComponent },
          { path: 'properties', component: AgencyPropertyComponent, resolve: { properties: AgencyResolverService } }
        ]
      }
    ]
  },
  {
    path: 'properties', component: PropertyComponent, children: [
      { path: 'sale', component: SaleComponent, resolve: { properties: PropertySaleResolverService } },
      { path: 'rent', component: RentComponent, resolve: { properties: PropertyRentResolverService } },
      { path: ':propertyId', component: PropertyPageComponent, resolve: { property: PropertyResolverService } }
    ]
  },
  { path: 'chat/:senderId', component: ChatListComponent, resolve: { chatUsers: ChatResolverService } },
  { path: 'chat/:senderId/:recipientId', component: ChatRoomComponent },
  { path: 'appointments', component: AppointmentsComponent },
  { path: '**', redirectTo: '/home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
