import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './component/home/home.component';
import { HeaderComponent } from './component/header/header.component';
import { FooterComponent } from './component/footer/footer.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { AgencyComponent } from './component/agency/agency.component';
import { AgenciesListComponent } from './component/agency/agencies-list/agencies-list.component';
import { AgenciesDetailComponent } from './component/agency/agencies-detail/agencies-detail.component';
import { AgencyResolverService } from './resolver/agency-resolver.service';
import { AgencyPropertyComponent } from './component/agency/agency-property/agency-property.component';
import { AgencyAgentComponent } from './component/agency/agency-agent/agency-agent.component';
import { AgencyAgentListComponent } from './component/agency/agency-agent/agency-agent-list/agency-agent-list.component';
import { AgencyAgentDetailComponent } from './component/agency/agency-agent/agency-agent-detail/agency-agent-detail.component';
import { AgencyPropertyListComponent } from './component/agency/agency-property/agency-property-list/agency-property-list.component';
import { AgencyPropertyDetailComponent } from './component/agency/agency-property/agency-property-detail/agency-property-detail.component';
import { PropertyComponent } from './component/property/property.component';
import { SaleComponent } from './component/property/sale/sale.component';
import { RentComponent } from './component/property/rent/rent.component';
import { DetailComponent } from './component/property/detail/detail.component';
import { PropertySaleResolverService } from './resolver/property-sale-resolver.service';
import { PropertyRentResolverService } from './resolver/property-rent-resolver.service';
import { PropertyPageComponent } from './component/property/property-page/property-page.component';
import { RouterModule } from '@angular/router';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AgentPropertiesComponent } from './component/agency/agency-agent/agent-properties/agent-properties.component';
import { RegisterComponent } from './component/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './service/auth.service';
import { UserService } from './service/user.service';
import { AuthComponent } from './component/auth/auth.component';
import { AdminComponent } from './component/admin/admin.component';
import { AccountComponent } from './component/account/account.component';
import { RecoverComponent } from './component/recover/recover.component';
import { ResetPasswordComponent } from './component/recover/reset-password/reset-password.component';
import { AccountService } from './service/account.service';
import { AgencyService } from './service/agency.service';
import { PropertyService } from './service/property.service';
import { AuthInterceptor } from './service/auth.interceptor';
import { AuctionComponent } from './component/auction/auction.component';
import { RoomComponent } from './component/auction/room/room.component';
import { RoomsListComponent } from './component/auction/rooms-list/rooms-list.component';
import { AuctionService } from './service/auction.service';
import { AuctionResolverService } from './resolver/auction-resolver.service';
import { AgentPropertyResolverService } from './resolver/agent-property-resolver.service';
import { NewRoomComponent } from './component/auction/new-room/new-room.component';
import { FavouritesComponent } from './component/auction/favourites/favourites.component';
import { ClosedComponent } from './component/auction/closed/closed.component';
import { ChatComponent } from './component/chat/chat.component';
import { ChatNotificationComponent } from './component/chat/chat-notification/chat-notification.component';
import { ChatRoomComponent } from './component/chat/chat-room/chat-room.component';
import { ChatListComponent } from './component/chat/chat-list/chat-list.component';
import { AppointmentsComponent } from './component/appointments/appointments.component';
import { AppointmentCardComponent } from './component/appointments/appointment-card/appointment-card.component';
import { AppointmentDetailsComponent } from './component/appointments/appointment-details/appointment-details.component';
import { AppointmentStatusComponent } from './component/appointments/appointment-status/appointment-status.component';
import { ChatService } from './service/chat.service';
import { AppointmentService } from './service/appointment.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    AgencyComponent,
    AgenciesListComponent,
    AgenciesDetailComponent,
    AgencyAgentComponent,
    AgencyAgentListComponent,
    AgencyAgentDetailComponent,
    AgencyPropertyComponent,
    AgencyPropertyListComponent,
    AgencyPropertyDetailComponent,
    PropertyComponent,
    SaleComponent,
    RentComponent,
    DetailComponent,
    PropertyPageComponent,
    AgentPropertiesComponent,
    RegisterComponent,
    AuthComponent,
    AdminComponent,
    AccountComponent,
    RecoverComponent,
    ResetPasswordComponent,
    AuctionComponent,
    RoomComponent,
    RoomsListComponent,
    NewRoomComponent,
    FavouritesComponent,
    ClosedComponent,
    ChatComponent,
    ChatNotificationComponent,
    ChatRoomComponent,
    ChatListComponent,
    AppointmentsComponent,
    AppointmentCardComponent,
    AppointmentDetailsComponent,
    AppointmentStatusComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    CommonModule,
    RouterModule,
    CarouselModule,
    BrowserAnimationsModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    AgencyResolverService,
    PropertySaleResolverService,
    PropertyRentResolverService,
    AuctionResolverService,
    AgentPropertyResolverService,
    AccountService,
    AgencyService,
    AuthService,
    PropertyService,
    UserService,
    AuctionService,
    ChatService,
    AppointmentService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
