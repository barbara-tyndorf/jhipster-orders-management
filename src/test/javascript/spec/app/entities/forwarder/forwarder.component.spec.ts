import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrdersManagementTestModule } from '../../../test.module';
import { ForwarderComponent } from 'app/entities/forwarder/forwarder.component';
import { ForwarderService } from 'app/entities/forwarder/forwarder.service';
import { Forwarder } from 'app/shared/model/forwarder.model';

describe('Component Tests', () => {
  describe('Forwarder Management Component', () => {
    let comp: ForwarderComponent;
    let fixture: ComponentFixture<ForwarderComponent>;
    let service: ForwarderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrdersManagementTestModule],
        declarations: [ForwarderComponent],
        providers: []
      })
        .overrideTemplate(ForwarderComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ForwarderComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ForwarderService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Forwarder(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.forwarders && comp.forwarders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
