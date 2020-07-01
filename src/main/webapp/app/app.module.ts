import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { RajsaloonSharedModule } from 'app/shared/shared.module';
import { RajsaloonCoreModule } from 'app/core/core.module';
import { RajsaloonAppRoutingModule } from './app-routing.module';
import { RajsaloonHomeModule } from './home/home.module';
import { RajsaloonEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    RajsaloonSharedModule,
    RajsaloonCoreModule,
    RajsaloonHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    RajsaloonEntityModule,
    RajsaloonAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class RajsaloonAppModule {}
