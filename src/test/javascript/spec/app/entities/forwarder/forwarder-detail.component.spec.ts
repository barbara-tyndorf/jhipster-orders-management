import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrdersManagementTestModule } from '../../../test.module';
import { ForwarderDetailComponent } from 'app/entities/forwarder/forwarder-detail.component';
import { Forwarder } from 'app/shared/model/forwarder.model';

describe('Component Tests', () => {
  describe('Forwarder Management Detail Component', () => {
    let comp: ForwarderDetailComponent;
    let fixture: ComponentFixture<ForwarderDetailComponent>;
    const route = ({ data: of({ forwarder: new Forwarder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrdersManagementTestModule],
        declarations: [ForwarderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ForwarderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ForwarderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load forwarder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.forwarder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
